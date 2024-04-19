package com.mieker.ifpr.shelfie.service;


import com.mieker.ifpr.shelfie.dto.ReadingProgress.CollectionOfMyBooksDTO;
import com.mieker.ifpr.shelfie.dto.MyBooksDTO;
import com.mieker.ifpr.shelfie.dto.ReadingProgress.ReadingProgressDTO;
import com.mieker.ifpr.shelfie.dto.ReadingProgress.UpdateReadingProgressDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.ReadingProgress;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.mapper.MyBooksMapper;
import com.mieker.ifpr.shelfie.mapper.ReadingProgressMapper;
import com.mieker.ifpr.shelfie.repository.BookRepository;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import com.mieker.ifpr.shelfie.repository.ReadingProgressRepository;
import lombok.AllArgsConstructor;
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
    private final MyBooksRepository myBooksRepository;
    private final BookRepository bookRepository;
    private final MyBookService myBookService;
    private final MyBooksMapper myBooksMapper;
    private final ReadingProgressMapper rpMapper;

    public String create (ReadingProgressDTO rpDTO) {
        ReadingProgress rp = new ReadingProgress();
        MyBooksDTO mbDTO = myBookService.getMyBooksById(rpDTO.getMyBooksId());
        MyBooks myBooks = myBooksMapper.myBookDTOtoMyBook(mbDTO);
        Book books = bookRepository.findById(myBooks.getBook().getId()).orElseThrow(() -> new RuntimeException("Não encontrado Book com id: " + myBooks.getBook().getId()));
        UUID userId = getUserId();
        if (!myBooks.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Você não tem permissão para adicionar progresso de leitura a este livro.");
        }
        String message = "";
        if (myBooks.getBookStatus() == BookStatus.LENDO && myBooks.isEnabled() && rpDTO.getPage() <= books.getPages()) {
            rp.setMyBooks(myBooks);
            rp.setPage(rpDTO.getPage());
            rp.setCommentary(rpDTO.getCommentary());
            rpRepository.save(rp);
            message = "Progresso de leitura criado com sucesso.";
        } else if (rpDTO.getPage() >= books.getPages()){
            message = "Quantidades de páginas adicionadas acima da quantidade total de páginas do livro.";
        } else {
            message = "Não foi possível criar o progresso de leitura.";
        }
        return message;
    }

//    todo:
//    criar uma rota para esse serviço
//    esse para o usuário ver só a dele

//    public List<CollectionOfMyBooksDTO> getReadingProgressByMyBooksId(UUID myBooksId) {
//        MyBooks mb = myBooksRepository.findById(myBooksId).orElseThrow(() -> new RuntimeException("Não encontrado MyBooks com id: " + myBooksId));
//        Book book = bookRepository.findById(mb.getBook().getId()).orElseThrow(() -> new RuntimeException("Não encontrado Book com id: " + mb.getBook().getId()));
//        String googleId = book.getGoogleId();
//        List<ReadingProgress> rpList = rpRepository.findByMyBooksId(myBooksId);
//        return rpList.stream()
//                .map( rp -> {
//                    CollectionOfMyBooksDTO dto = rpMapper.readingProgressToCollectionOfMyBooks(rp);
//                    dto.setGoogleId(googleId);
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }

//    essa rota mostra os mybooks
//    então como os mybooks o id ta relacionado
    public List<CollectionOfMyBooksDTO> getReadingProgressByUserId(UUID userId) {
        List<MyBooks> myBooksList = myBooksRepository.findByUserId(userId);
//        System.out.println(myBooksList);
        List<CollectionOfMyBooksDTO> resultList = new ArrayList<>(); // Lista para armazenar os resultados

        for (MyBooks mb : myBooksList) {
            List<ReadingProgress> rpList = rpRepository.findByMyBooksId(mb.getId());
            System.out.println(mb.getId());
            System.out.println(rpList);

            // Mapeie e adicione os DTOs à lista de resultados
            resultList.addAll(rpList.stream()
                    .map(rp -> {

                        System.out.println("\n1\n");
                        CollectionOfMyBooksDTO dto = rpMapper.readingProgressToCollectionOfMyBooks(rp);
                        Book book = bookRepository.findById(mb.getBook().getId()).orElseThrow(() -> new RuntimeException("Não encontrado Book com id: " + mb.getBook().getId()));
                        dto.setGoogleId(book.getGoogleId());
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
        List<ReadingProgress> rpList = rpRepository.findAll();
        return rpList.stream()
                .map( rp -> {
                    CollectionOfMyBooksDTO dto = rpMapper.readingProgressToCollectionOfMyBooks(rp);
                    MyBooks mb = myBooksRepository.findById(rp.getMyBooks().getId()).orElseThrow(() -> new RuntimeException("Não encontrado MyBooks com id: " + rp.getMyBooks().getId()));
                    Book book = bookRepository.findById(mb.getBook().getId()).orElseThrow(() -> new RuntimeException("Não encontrado Book com id: " + mb.getBook().getId()));
                    dto.setGoogleId(book.getGoogleId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<CollectionOfMyBooksDTO> getReadingProgressByMyBooksId(UUID myBooksId) {
        MyBooks mb = myBooksRepository.findById(myBooksId).orElseThrow(() -> new RuntimeException("Não encontrado MyBooks com id: " + myBooksId));
        Book book = bookRepository.findById(mb.getBook().getId()).orElseThrow(() -> new RuntimeException("Não encontrado Book com id: " + mb.getBook().getId()));
        String googleId = book.getGoogleId();
        List<ReadingProgress> rpList = rpRepository.findByMyBooksId(myBooksId);
        return rpList.stream()
                .map( rp -> {
                    CollectionOfMyBooksDTO dto = rpMapper.readingProgressToCollectionOfMyBooks(rp);
                    dto.setGoogleId(googleId);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private ReadingProgress getReadingProgressById(UUID id) {
        return rpRepository.findById(id).orElseThrow(() -> new RuntimeException("Não encontrado ReadingProgress com id: " + id));
    }

    private void checkPermission(UUID myBooksId) {
        MyBooks mb = myBooksRepository.findById(myBooksId).orElseThrow();
        UUID userId = getUserId();
        if (!mb.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Você não tem permissão para acessar este progresso de leitura.");
        }
    }

    private UUID getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return currentUser.getId();
    }
}
