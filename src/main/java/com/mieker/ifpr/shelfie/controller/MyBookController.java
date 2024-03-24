package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.BookApiDTO;
import com.mieker.ifpr.shelfie.dto.MyBookDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.service.MyBookService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/mybooks")
public class MyBookController {

    private final MyBookService myBookService;
    private final ModelMapper mapper;
    @PostMapping("/{userId}/{googleId}/{bookStatus}")
    public ResponseEntity<MyBookDTO> createMyBooks(@PathVariable UUID userId, @PathVariable String googleId, @PathVariable BookStatus bookStatus) throws ParseException {
        MyBookDTO myBookDTO = myBookService.create(userId, googleId, bookStatus);
        return ResponseEntity.ok(myBookDTO);
    }

//    TODO
//    criar rota de update do status do mybooks
//    criar rota de disable do mybooks
//    criar rota de get mybooks do usuario
//    criar rota de get mybooks de todos os mybooks
}
