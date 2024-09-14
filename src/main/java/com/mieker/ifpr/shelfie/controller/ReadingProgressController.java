package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.ReadingProgress.CollectionOfMyBooksDTO;
import com.mieker.ifpr.shelfie.dto.ReadingProgress.ReadingProgressDTO;
import com.mieker.ifpr.shelfie.dto.ReadingProgress.UpdateReadingProgressDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.exception.GlobalExceptionHandler;
import com.mieker.ifpr.shelfie.service.ReadingProgressService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<String> createReadingProgression(@RequestBody ReadingProgressDTO readingProgressDTO) throws BadRequestException {
        String message = rpService.create(readingProgressDTO);
        return ResponseEntity.status(201).body(message);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{bookId}")
    public ResponseEntity<List<CollectionOfMyBooksDTO>> getReadingProgressByMyBooksId(@PathVariable UUID bookId) {
        List<CollectionOfMyBooksDTO> rpList = rpService.getReadingProgressByBookId(bookId);
        return ResponseEntity.ok(rpList);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<CollectionOfMyBooksDTO>> getAllReadingProgress() {
        return ResponseEntity.ok(rpService.getAllReadingProgress());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<CollectionOfMyBooksDTO>> getReadingProgressByUser() {
        List<CollectionOfMyBooksDTO> rpList = rpService.getReadingProgressByUserId();
        return ResponseEntity.ok(rpList);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReadingProgress(@PathVariable UUID id) {
        String message = rpService.deleteReadingProgress(id);
        return ResponseEntity.status(200).body(message);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateReadingProgress(@PathVariable UUID id, @RequestBody UpdateReadingProgressDTO updReadingProgressDTO) {
        String message = rpService.updateReadingProgress(id, updReadingProgressDTO);
        return ResponseEntity.status(200).body(message);
    }
}

