package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.Book.BookDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.mapper.BookMapper;
import com.mieker.ifpr.shelfie.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookApiService bookApiService;

//    todo
//    arrumar isso aqui
//    o mapper ta fazendo serviços que é setar o books
//    arrumar isso


    public Book createBook(String googleId) throws ParseException {
        Book book = bookRepository.findByGoogleId(googleId).orElse(null);
        if (book != null) {
            return book;
        } else {
            BookDTO bookDTO = bookApiService.getBookByGoogleId(googleId);
            book = bookMapper.bookDTOtoBook(bookDTO);
            return bookRepository.save(book);
        }
    }

    public BookDTO getBookById(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("MyBooks not found with id: " + id));
        return bookMapper.bookToBookDTO(book);
    }

    public boolean isBookStatusValid(String status) {
        try {
            BookStatus.valueOf(status);
            return true; // O valor é válido
        } catch (IllegalArgumentException ex) {
            return false; // O valor não é válido
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
