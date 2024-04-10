package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.MyBooksDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/reading")
public class ReadingProgressionController {

    @PostMapping("/{userId}/{bookId}")
    public void createReadingProgression() {
//        TODO
//        tem que retornar que deu certo
//        tem que adicionar aqui a quantidade páginas também
    }
}
