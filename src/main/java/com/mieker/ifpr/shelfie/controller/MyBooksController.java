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
//    TODO
//    arrumar isso aqui
//    quero que me retorne o DTO
//    @GetMapping("/{id}")
//    public ResponseEntity<MyBooksDTO> getMyBooksById(@PathVariable UUID id) {
//        MyBooksDTO myBooks = myBookService.getMyBooksById(id);
//        return ResponseEntity.ok(myBooks);
//    }

//    rota authenticated
    @PutMapping("/{id}/disable")
    public ResponseEntity<String> deleteMyBooks(@PathVariable("id") UUID id) {
        try {
            MyBooks myBooks = myBookService.updateMyBookDisable(id);
            System.out.println(myBooks);
            return ResponseEntity.ok("My Books Disabled.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

//    TODO
//    criar rota que retorna todos os mybooks do user
//    criar rota que retorna todos os users que tão relacionados a tal id de livro

//    @PutMapping("/{id}/update/{bookStatus}")
//    public ResponseEntity<UpdateMyBooksDTO> updateMyBooks(@PathVariable("id") UUID id, @PathVariable BookStatus bookStatus) {
//        try {
//            MyBooks myBooks = myBookService.updateMyBook(id, updateMyBooksDTO);
//            return ResponseEntity.ok(myBooks);
//    }
//    TODO
//    criar rota de update do status do mybooks
//    criar rota de disable do mybooks
//    criar rota de get mybooks do usuario
//    criar rota de get mybooks de todos os mybooks

//    TODO
//    fazer uma regra que aparece só os livros
}
