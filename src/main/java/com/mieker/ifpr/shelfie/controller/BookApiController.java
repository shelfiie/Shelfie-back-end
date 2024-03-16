package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.BookApiDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.service.BookApiService;
import com.mieker.ifpr.shelfie.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/books/google")
public class BookApiController {
    private final BookService bookService;
    private final BookApiService bookApiService;

    public BookApiController(BookService bookService, BookApiService bookApiService) {
        this.bookService = bookService;
        this.bookApiService = bookApiService;
    }

//    pegar livro pelo isbn10
//    @GetMapping("/isbn/{isbn10}")
//    public ResponseEntity<BookApiDTO> getBookByIsbn10(@PathVariable String isbn10) {
//        BookApiDTO book = bookApiService.getBookByISBN10(isbn10);
//        return ResponseEntity.ok(book);
//    }

//    pegar livro pelo id do google
    @GetMapping("/{googleId}")
    public ResponseEntity<BookApiDTO> getBookByGoogleId(@PathVariable String googleId) {
        System.out.println(googleId);
        BookApiDTO book = bookApiService.getBookByGoogleId(googleId);
        return ResponseEntity.ok(book);
    }


    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookApiDTO bookDTO) {
        try {
            Book book = bookService.createBook(bookDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
