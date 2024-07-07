package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.MyBooks.MyBooksDTO;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.service.FavoriteBookService;
import com.mieker.ifpr.shelfie.service.MyBookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/books/favorite")
public class FavoriteBookController {
    private final MyBookService myBookService;
    private final FavoriteBookService favoriteBookService;

    // rota para favoritar livros
    @PreAuthorize("isAuthenticated()")
    @PostMapping("{bookId}")
    public ResponseEntity<String> favoriteBook(@PathVariable UUID bookId) {

        String message = favoriteBookService.create(bookId);
        return ResponseEntity.status(201).body(message);
    }
}
