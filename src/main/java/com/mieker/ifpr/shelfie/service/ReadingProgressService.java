package com.mieker.ifpr.shelfie.service;


import com.mieker.ifpr.shelfie.dto.ReadingProgress.CollectionOfMyBooksDTO;
import com.mieker.ifpr.shelfie.dto.MyBooksDTO;
import com.mieker.ifpr.shelfie.dto.ReadingProgress.ReadingProgressDTO;
import com.mieker.ifpr.shelfie.dto.ReadingProgress.UpdateReadingProgressDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.ReadingProgress;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.mapper.MyBooksMapper;
import com.mieker.ifpr.shelfie.mapper.ReadingProgressMapper;
import com.mieker.ifpr.shelfie.repository.BookRepository;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import com.mieker.ifpr.shelfie.repository.ReadingProgressRepository;
import lombok.AllArgsConstructor;
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
        System.out.println(rpDTO.getPage());
        System.out.println(books.getPages());

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
        ReadingProgress rp = rpRepository.findById(id).orElseThrow(() -> new RuntimeException("Não encontrado ReadingProgress com id: " + id));
        rpRepository.delete(rp);
        return  "Progresso de leitura deletado com sucesso.";
    }

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

    public String updateReadingProgress(UUID id, UpdateReadingProgressDTO updReadingProgressDTO) {
        ReadingProgress rp = rpRepository.findById(id).orElseThrow(() -> new RuntimeException("Não encontrado ReadingProgress com id: " + id));
        if (!updReadingProgressDTO.getCommentary().isEmpty()) {
            rp.setCommentary(updReadingProgressDTO.getCommentary());
        }
        rpRepository.save(rp);
        return "Progresso de leitura atualizado com sucesso.";
    }
}
