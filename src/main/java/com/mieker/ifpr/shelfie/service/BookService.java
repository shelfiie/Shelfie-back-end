package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.BookApiDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.mapper.BookMapper;
import com.mieker.ifpr.shelfie.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
//    todo
//    arrumar isso aqui
//    o mapper ta fazendo serviços que é setar o books
//    arrumar isso
    public Book createBook(BookApiDTO bookDTO) throws ParseException {
        Book book = bookMapper.bookDTOtoBook(bookDTO);
//        user = convertUserRegistration(signUpDTO);
        return bookRepository.save(book);
    }

    public Book getBookById(UUID id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
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
