package com.mieker.ifpr.shelfie.service;


import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.ReadingProgress.CollectionOfMyBooksDTO;
import com.mieker.ifpr.shelfie.dto.MyBooks.MyBooksDTO;
import com.mieker.ifpr.shelfie.dto.ReadingProgress.ReadingProgressDTO;
import com.mieker.ifpr.shelfie.dto.ReadingProgress.UpdateReadingProgressDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.ReadingProgress;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.exception.AccessForbiddenException;
import com.mieker.ifpr.shelfie.exception.ExceededPageLimitException;
import com.mieker.ifpr.shelfie.exception.IdNotFoundException;
import com.mieker.ifpr.shelfie.mapper.MyBooksMapper;
import com.mieker.ifpr.shelfie.mapper.ReadingProgressMapper;
import com.mieker.ifpr.shelfie.repository.BookRepository;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import com.mieker.ifpr.shelfie.repository.ReadingProgressRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReadingProgressService {
    private final ReadingProgressRepository rpRepository;
    private final MyBooksRepository mbRepository;
    private final BookRepository bookRepository;
    private final ReadingProgressMapper rpMapper;
    private Validation validation;

    public String create (ReadingProgressDTO rpDTO) throws BadRequestException {
        ReadingProgress rp = new ReadingProgress();
        UUID userId = validation.userAuthenticator();
        MyBooks myBooks = mbRepository.findMyBooksByBookIdAndUserId(rpDTO.getBookId(), userId);
        Book books = bookRepository.findById(myBooks.getBook().getId()).orElseThrow(() -> new IdNotFoundException("Não encontrado Book com id: " + myBooks.getBook().getId()));

        if (!myBooks.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Você não tem permissão para adicionar progresso de leitura a este livro.");
        }

        String message = "";

        if (myBooks.getBookStatus() == BookStatus.LENDO) {
            if (myBooks.isEnabled() && rpDTO.getPage() < books.getPages()) {
                rp.setMyBooks(myBooks);
                rp.setPage(rpDTO.getPage());
                rp.setCommentary(rpDTO.getCommentary());
                rpRepository.save(rp);
                message = "Progresso de leitura criado com sucesso.";
            } else if (myBooks.isEnabled() && rpDTO.getPage().equals(books.getPages())) {
                rp.setMyBooks(myBooks);
                rp.setPage(rpDTO.getPage());
                rp.setCommentary(rpDTO.getCommentary());
                rpRepository.save(rp);
                myBooks.setBookStatus(BookStatus.LIDO);
                mbRepository.save(myBooks);
                message = "Progresso de leitura criado com sucesso.";
            } else if (rpDTO.getPage() >= books.getPages()){
                throw new ExceededPageLimitException("O número da página não pode ser maior que o número total de páginas do livro.");
            }
        } else {
            throw new AccessForbiddenException("O status do livro tem que ser LENDO.");
        }

        return message;
    }

    protected void createReadingProgressionDisabled (UUID myBooksId) {
        MyBooks myBooks = mbRepository.findMyBooksById(myBooksId);
        Book books = bookRepository.findById(myBooks.getBook().getId()).orElseThrow(() -> new IdNotFoundException("Não encontrado Book com id: " + myBooks.getBook().getId()));
        ReadingProgress rp = new ReadingProgress();
        rp.setMyBooks(myBooks);
        rp.setPage(books.getPages());
        rp.setEnabled(false);
        rpRepository.save(rp);
    }

//    essa rota mostra os mybooks
//    então como os mybooks o id ta relacionado
    public List<CollectionOfMyBooksDTO> getReadingProgressByUserId() {
        UUID userId = validation.userAuthenticator();
        List<MyBooks> myBooksList = mbRepository.findAllByUserIdAndEnabled(userId, true);
        List<CollectionOfMyBooksDTO> resultList = new ArrayList<>(); // Lista para armazenar os resultados

        for (MyBooks mb : myBooksList) {
            List<ReadingProgress> rpList = rpRepository.findByMyBooksIdAndEnabled(mb.getId());

            // Mapeie e adicione os DTOs à lista de resultados
            resultList.addAll(rpList.stream()
                    .map(rp -> {
                        CollectionOfMyBooksDTO dto = rpMapper.readingProgressToCollectionOfMyBooks(rp);
                        Book book = bookRepository.findById(mb.getBook().getId()).orElseThrow(() -> new IdNotFoundException("Não encontrado Book com id: " + mb.getBook().getId()));
                        dto.setGoogleId(book.getGoogleId());
                        System.out.println(rp.getEnabled());
                        dto.setBookId(mb.getBook().getId());
                        return dto;
                    })
                    .toList());
        }
        return resultList;
    }

    public String deleteReadingProgress(UUID id) {
        ReadingProgress rp = getReadingProgressById(id);
        checkPermission(rp.getMyBooks().getId());
        rpRepository.delete(rp);
        return "Progresso de leitura deletado com sucesso.";
    }

    public String updateReadingProgress(UUID id, UpdateReadingProgressDTO updReadingProgressDTO) {
        ReadingProgress rp = getReadingProgressById(id);
        checkPermission(rp.getMyBooks().getId());
        if (!updReadingProgressDTO.getCommentary().isEmpty()) {
            rp.setCommentary(updReadingProgressDTO.getCommentary());
        }
        rpRepository.save(rp);
        return "Progresso de leitura atualizado com sucesso.";
    }

    //    rota do admin
    public List<CollectionOfMyBooksDTO> getAllReadingProgress() {
        List<ReadingProgress> rpList = rpRepository.findAllAndEnabled();
        return rpList.stream()
                .map( rp -> {
                    CollectionOfMyBooksDTO dto = rpMapper.readingProgressToCollectionOfMyBooks(rp);
                    MyBooks mb = mbRepository.findById(rp.getMyBooks().getId()).orElseThrow(() -> new IdNotFoundException("Não encontrado MyBooks com id: " + rp.getMyBooks().getId()));
                    Book book = bookRepository.findById(mb.getBook().getId()).orElseThrow(() -> new IdNotFoundException("Não encontrado Book com id: " + mb.getBook().getId()));
                    dto.setGoogleId(book.getGoogleId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<CollectionOfMyBooksDTO> getReadingProgressByBookId(UUID bookId) {
        UUID userId = validation.userAuthenticator();
        MyBooks mb = mbRepository.findMyBooksByBookIdAndUserId(bookId, userId);
        Book book = bookRepository.findById(mb.getBook().getId()).orElseThrow(() -> new IdNotFoundException("Não encontrado Book com id: " + mb.getBook().getId()));
        String googleId = book.getGoogleId();
        List<ReadingProgress> rpList = rpRepository.findByMyBooksIdAndIsEnabled(mb.getId());
        return rpList.stream()
                .map( rp -> {
                    CollectionOfMyBooksDTO dto = rpMapper.readingProgressToCollectionOfMyBooks(rp);
                    dto.setGoogleId(googleId);
                    dto.setBookId(book.getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private ReadingProgress getReadingProgressById(UUID id) {
        return rpRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Não encontrado ReadingProgress com id: " + id));
    }

    private void checkPermission(UUID myBooksId) {
        MyBooks mb = mbRepository.findById(myBooksId).orElseThrow();
        UUID userId = validation.userAuthenticator();
        if (!mb.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Você não tem permissão para acessar este progresso de leitura.");
        }
    }

    protected void disableReadingProgress(UUID myBooksId) {
        List<ReadingProgress> rpList = rpRepository.findByMyBooksId(myBooksId);
        for (ReadingProgress rp : rpList) {
            rp.setEnabled(!rp.getEnabled());
            rpRepository.save(rp);
        }
    }

}
