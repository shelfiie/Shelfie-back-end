package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.CollectionOfMyBooksDTO;
import com.mieker.ifpr.shelfie.dto.MyBooksDTO;
import com.mieker.ifpr.shelfie.dto.ReadingProgressDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.service.ReadingProgressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        String message = rpService.create(readingProgressDTO);
        return ResponseEntity.status(201).body(message);
    }

    @GetMapping("/{myBooksId}")
    public ResponseEntity<List<CollectionOfMyBooksDTO>> getReadingProgressByMyBooksId(@PathVariable UUID myBooksId) {
        return ResponseEntity.ok(rpService.getReadingProgressByMyBooksId(myBooksId));
    }

    @GetMapping
    public ResponseEntity<List<CollectionOfMyBooksDTO>> getReadingProgressByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        UUID userId = currentUser.getId();
        List<CollectionOfMyBooksDTO> rpList = rpService.getReadingProgressByUserId(userId);

        return ResponseEntity.ok(rpList);
    }
}
