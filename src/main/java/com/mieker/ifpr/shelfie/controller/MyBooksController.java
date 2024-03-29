package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.DisableDTO;
import com.mieker.ifpr.shelfie.dto.MyBooksDTO;
import com.mieker.ifpr.shelfie.dto.UpdateMyBooksDTO;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.service.MyBookService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/mybooks")
public class MyBooksController {
//    TODO
//    testar todas essas rotas

    private final MyBookService myBookService;
    private final ModelMapper mapper;

//    rota que associa um usuário reader a um livro
    @PostMapping("/{userId}/{googleId}/{bookStatus}")
    public ResponseEntity<MyBooksDTO> createMyBooks(@PathVariable UUID userId, @PathVariable String googleId, @PathVariable BookStatus bookStatus) throws ParseException {
        MyBooksDTO myBooksDTO = myBookService.create(userId, googleId, bookStatus);
        return ResponseEntity.ok(myBooksDTO);
    }

//    rota dos admins
//    retorna o id dos usuários, id dos livros e situação dos livros
    @GetMapping
    public ResponseEntity<List<MyBooksDTO>> getAllMyBooks() {
        List<MyBooksDTO> myBooks = myBookService.getAllMyBooks();
        return ResponseEntity.ok(myBooks);
    }

//    rota do usuario reader
//    retorna o mybooks do id passado como parâmetro
    @GetMapping("/{id}")
    public ResponseEntity<MyBooksDTO> getMyBooksById(@PathVariable UUID id) {
        MyBooksDTO myBooksDTO = myBookService.getMyBooksById(id);
        return ResponseEntity.ok(myBooksDTO);
    }

//    rota authenticated
//    rota para desativar um my books
    @PutMapping("/{id}/disable")
    public ResponseEntity<String> deleteMyBooks(@PathVariable("id") UUID id) {
        try {
            MyBooks myBooks = myBookService.updateMyBooksDisable(id);
            System.out.println(myBooks);
            return ResponseEntity.ok("My Books Disabled.");
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
    public ResponseEntity<UpdateMyBooksDTO> updateMyBooks(@PathVariable UUID id, @PathVariable BookStatus bookStatus) {
        UpdateMyBooksDTO updateMyBooksDTO = myBookService.updateMyBooks(id, bookStatus);
        return ResponseEntity.ok(updateMyBooksDTO);
    }

//    pega todos os mybooks do usuário que estão enabled
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{userId}/mine")
    public ResponseEntity<List<MyBooksDTO>> getMyBooksByUserId(@PathVariable UUID userId) {
        List<MyBooksDTO> myBooksDTO = myBookService.getMyBooksByUserId(userId);
        return ResponseEntity.ok(myBooksDTO);
    }

//    rota para retornar todos os mybooks de um livro
    @GetMapping("/{bookId}/list")
    public ResponseEntity<List<MyBooksDTO>> getMyBooksByBookId(@PathVariable UUID bookId) {
        List<MyBooksDTO> myBooksDTO = myBookService.getMyBooksByBookId(bookId);
        return ResponseEntity.ok(myBooksDTO);
    }

}
