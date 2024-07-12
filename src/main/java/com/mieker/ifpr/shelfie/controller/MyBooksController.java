package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.MyBooks.MyBooksDTO;
import com.mieker.ifpr.shelfie.dto.MyBooks.UpdateMyBooksDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.service.MyBookService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/mybooks")
public class MyBooksController {

    private final MyBookService myBookService;

//    rota que associa um usuário reader a um livro
    @PreAuthorize("isAuthenticated()")
    @PostMapping("{googleId}/{bookStatus}")
    public ResponseEntity<MyBooksDTO> createMyBooks(@PathVariable String googleId, @PathVariable BookStatus bookStatus) throws ParseException {

        MyBooksDTO myBooksDTO = myBookService.create(googleId, bookStatus);
        return ResponseEntity.status(201).body(myBooksDTO);
    }

//    rota dos admins
//    retorna o id dos usuários, id dos livros e situação dos livros
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<MyBooksDTO>> getAllMyBooks() {
        List<MyBooksDTO> myBooks = myBookService.getAllMyBooks();
        return ResponseEntity.ok(myBooks);
    }

//    rota do usuario reader
//    retorna o mybooks do id passado como parâmetro

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<MyBooksDTO> getMyBooksById(@PathVariable UUID id) {
        MyBooksDTO myBooksDTO = myBookService.getMyBooksById(id);
        return ResponseEntity.ok(myBooksDTO);
    }

//    rota authenticated
//    rota para desativar um my books
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}/disable")
    public ResponseEntity<String> deleteMyBooks(@PathVariable("id") UUID id) {
        try {
//            não sei se esse eu retorno alguma coisa ou não
            MyBooksDTO myBooks = myBookService.updateMyBooksDisable(id);
//            System.out.println(myBooks);
            return ResponseEntity.ok("Livro apagado com sucesso.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

//    todo
//    quando o user der disabled no mybooks apaga tudo, so fica o id do mybooks e do livro

//    TODO
//    refletir sobre isso aqui !!!
//    retorno só o id do livro e o status do livro ? dont knowrr
//    rota para atualizar o status do livro
    @PutMapping("/{id}/update/{bookStatus}")
    public ResponseEntity<UpdateMyBooksDTO> updateMyBooks(@PathVariable UUID myBooksid, @PathVariable BookStatus bookStatus) {
        UpdateMyBooksDTO updateMyBooksDTO = myBookService.updateMyBooks(myBooksid, bookStatus);
        return ResponseEntity.ok(updateMyBooksDTO);
    }

//    pega todos os mybooks do usuário que estão enabled
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mine")
//    todo
//    investigar essa rota
//    ver se ela precisa que passe um parametro
//    talvez criar uma nova rota q passa o id como parâmetro
    public ResponseEntity<List<MyBooksDTO>> getMyBooksByUserId() {
        List<MyBooksDTO> myBooksDTO = myBookService.getMyBooksByUserId();
        return ResponseEntity.ok(myBooksDTO);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/status/{booksStatus}")
    public ResponseEntity<List<MyBooksDTO>> getMyBooksByStatus (@PathVariable BookStatus booksStatus) {
        List<MyBooksDTO> myBooksDTO = myBookService.getMyBooksByStatus(booksStatus);
        return ResponseEntity.ok(myBooksDTO);
    }

//    rota para retornar todos os mybooks de um livro
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{bookId}/list")
    public ResponseEntity<List<MyBooksDTO>> getMyBooksByBookId(@PathVariable UUID bookId) {
        List<MyBooksDTO> myBooksDTO = myBookService.getMyBooksByBookId(bookId);
        return ResponseEntity.ok(myBooksDTO);
    }

}
