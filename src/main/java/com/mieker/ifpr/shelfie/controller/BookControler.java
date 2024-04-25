package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.Book.BookDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.service.BookApiService;
import com.mieker.ifpr.shelfie.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    //    pegar livro pelo id do google na api do google
//    only admin tem acesso a essa rota
    @GetMapping("/google/{googleId}")
    public ResponseEntity<BookDTO> getBookByGoogleId(@PathVariable String googleId) {
        System.out.println(googleId);
        BookDTO book = bookApiService.getBookByGoogleId(googleId);
        return ResponseEntity.ok(book);
    }

    //    pegar livro pelo id do banco
    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable UUID id) {
//        System.out.println(googleId);
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

//    only admin tem acesso a essa rota
    @PostMapping("/create/{googleId}")
    public ResponseEntity<String> createBook(@PathVariable String googleId) {
        try {
            bookService.createBook(googleId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Livro criado com sucesso.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
