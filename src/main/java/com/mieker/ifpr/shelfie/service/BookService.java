package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.Book.BookDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.exception.IdNotFoundException;
import com.mieker.ifpr.shelfie.exception.NotFoundException;
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

    public Book createBook(String googleId) {
        Book book = bookRepository.findByGoogleId(googleId).orElse(null);
        if (book != null) {
            return book;
        } else {
            BookDTO bookDTO = bookApiService.getBookByGoogleId(googleId);
            book = bookMapper.bookDTOtoBook(bookDTO);
            return bookRepository.save(book);
        }
    }

    public BookDTO getBookByGoogleId(String googleId) {
        Book book = bookRepository.findByGoogleId(googleId).orElseThrow(() -> new NotFoundException("Livro não encontrado com esse ID:" + googleId));
        return bookMapper.bookToBookDTO(book);
    }

    public BookDTO getBookById(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Livro não encontrado com esse:" + id));
        return bookMapper.bookToBookDTO(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
