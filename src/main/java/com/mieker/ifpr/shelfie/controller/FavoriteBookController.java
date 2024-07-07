package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.Book.FavoriteBookDTO;
import com.mieker.ifpr.shelfie.dto.MyBooks.MyBooksDTO;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.service.FavoriteBookService;
import com.mieker.ifpr.shelfie.service.MyBookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
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

    // rota para pegar os livros favoritos do usuário logado
    @PreAuthorize("isAuthenticated()")
    @GetMapping("mine")
    public ResponseEntity<List<FavoriteBookDTO>> getMyFavoriteBooks() {
        List<FavoriteBookDTO> favoriteBookList = favoriteBookService.getMyFavoriteBooks();
        return ResponseEntity.status(200).body(favoriteBookList);
    }


// TODO
// fazer essa rota, ver o que quero que retorne
// só admin vai ter acesso a essa rota

    // rota para pegar todos os livros favoritos
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping
//    public ResponseEntity<List<FavoriteBookDTO>> getFavoriteBooks() {
//        List<FavoriteBookDTO> favoriteBookList = favoriteBookService.getFavoriteBooks();
//        return ResponseEntity.status(200).body(favoriteBookList);
//    }


}
