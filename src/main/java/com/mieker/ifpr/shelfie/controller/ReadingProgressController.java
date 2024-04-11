package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.service.ReadingProgressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/reading")
public class ReadingProgressController {
    @Autowired
    private final ReadingProgressService rpService;

    @PostMapping("/{myBookId}/{pages}")
    public ResponseEntity<String> createReadingProgression(@PathVariable UUID myBookId, @PathVariable Integer pages) {
//        TODO
//        tem que retornar que deu certo
//        tem que adicionar aqui a quantidade páginas também
        rpService.create(myBookId, pages);
        return ResponseEntity.status(201).body("Progresso de leitura criado com sucesso.");
    }
}
