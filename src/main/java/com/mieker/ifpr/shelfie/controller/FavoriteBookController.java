package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.Book.FavoriteBookDTO;
import com.mieker.ifpr.shelfie.dto.Book.ListUserFavoriteBookDTO;
import com.mieker.ifpr.shelfie.service.FavoriteBookService;
import com.mieker.ifpr.shelfie.service.MyBookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/{bookId}")
    public ResponseEntity<String> favoriteBook(@PathVariable UUID bookId) {
        String message = favoriteBookService.favoriteBook(bookId);
        return ResponseEntity.status(200).body(message);
    }

    // rota para pegar os livros favoritos do usuário logado
    @PreAuthorize("isAuthenticated()")
    @GetMapping("mine")
    public ResponseEntity<List<FavoriteBookDTO>> getMyFavoriteBooks() {
        List<FavoriteBookDTO> favoriteBookList = favoriteBookService.getMyFavoriteBooks();
        return ResponseEntity.status(200).body(favoriteBookList);
    }

    // rota para pegar os livros favoritos pelo id do usuario
    @PreAuthorize("isAuthenticated()")
    @GetMapping("{userId}")
    public ResponseEntity<List<FavoriteBookDTO>> getFavoriteBooksByUserId(@PathVariable UUID userId) {
        List<FavoriteBookDTO> favoriteBookList = favoriteBookService.getFavoriteBooksByUserId(userId);
        return ResponseEntity.status(200).body(favoriteBookList);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/is-favorited/{bookId}")
    public ResponseEntity<Boolean> isFavorited(@PathVariable UUID bookId) {
        boolean isFavorited = favoriteBookService.isFavorited(bookId);
        return ResponseEntity.status(200).body(isFavorited);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/books/{bookId}")
    public ResponseEntity<List<ListUserFavoriteBookDTO>> getFavoriteBooksByBookId(@PathVariable UUID bookId) {
        List<ListUserFavoriteBookDTO> favoriteBookList = favoriteBookService.getFavoriteBooksByBookId(bookId);
        return ResponseEntity.status(200).body(favoriteBookList);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<ListUserFavoriteBookDTO>> getAllFavoriteBooksAndUsersList() {
        List<ListUserFavoriteBookDTO> favoriteBookList = favoriteBookService.getAllFavoriteBooks();
        return ResponseEntity.status(200).body(favoriteBookList);
    }

//    todo
//    rotas :
//        uma rota que pega todos os livros favoritados do usuário - ok
//        uma rota que pega todos os usuarios que favoritaram x livro - ok
//        rota que retorna se o livro ta favoritado ou não - ok
//        rota que retorna toda a relação de livros favoritados (livro x usuario)
//        rota que contabiliza a quantidade de favoritos do livro

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
