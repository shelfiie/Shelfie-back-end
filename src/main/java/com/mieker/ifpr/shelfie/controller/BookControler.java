package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.BookApiDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.service.BookApiService;
import com.mieker.ifpr.shelfie.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookControler {

    @Autowired
    private final BookService bookService;
    private final BookApiService bookApiService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> book = bookService.getAllBooks();
        return ResponseEntity.ok(book);
    }

    //    pegar livro pelo id do google
    @GetMapping("/google/{googleId}")
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
