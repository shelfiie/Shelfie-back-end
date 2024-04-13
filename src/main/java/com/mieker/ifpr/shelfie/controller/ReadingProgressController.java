package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.CollectionOfMyBooksDTO;
import com.mieker.ifpr.shelfie.dto.ReadingProgressDTO;
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

    @PostMapping
    public ResponseEntity<String> createReadingProgression(@RequestBody ReadingProgressDTO readingProgressDTO) {
//        TODO
//        tem que retornar que deu certo
//        tem que adicionar aqui a quantidade páginas também
        rpService.create(readingProgressDTO);
        return ResponseEntity.status(201).body("Progresso de leitura criado com sucesso.");
    }

    @GetMapping("{userId}/{myBooksId}")
    public ResponseEntity<CollectionOfMyBooksDTO> getListOfReadingProgress(@RequestParam UUID userId, @RequestParam UUID myBooksId) {
        return ResponseEntity.ok(rpService.getReadingProgressByMyBooksId(userId, myBooksId));
    }
}
