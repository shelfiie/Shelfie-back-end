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

import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/mybooks")
public class MyBookController {
    //    rota (googleid) -> service -> na service procura se tem esse id no banco -> se tiver adiciona no banco mais usuário
    //    rota (googleid) -> service -> na service procura se tem esse id no banco -> se não tiver adiciona no banco tb_books

    private final MyBookService myBookService;
    private final ModelMapper mapper;
    @PostMapping("/{userId}/{googleId}/{bookStatus}")
    public ResponseEntity<MyBookDTO> createMyBooks(@PathVariable UUID userId, @PathVariable String googleId, @PathVariable BookStatus bookStatus) {
//        MyBookDTO myBookDTO = new MyBookDTO();
//        myBookDTO.setGoogleId(googleId);
//        myBookDTO.setUserId(userId);
//        myBookDTO.setBookStatus(bookStatus);

//        retornar DTO
        MyBookDTO myBookDTO = myBookService.create(userId, googleId, bookStatus);
//        dps eu reflito sobre
//        MyBookDTO myBookDTO = mapper.map(myBooks, MyBookDTO.class);

        return ResponseEntity.ok(myBookDTO);
    }
}
