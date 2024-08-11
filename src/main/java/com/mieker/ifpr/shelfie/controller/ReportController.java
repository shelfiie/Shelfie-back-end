package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.Review.ReviewDTO;
import com.mieker.ifpr.shelfie.service.ReadingProgressService;
import com.mieker.ifpr.shelfie.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private final ReportService reportService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("{reviewId}")
    public ResponseEntity<String> createReport(@PathVariable UUID reviewId) {
        String message = reportService.createReport(reviewId);
//        return ResponseEntity.ok(responseReview);
        return ResponseEntity.status(201).body(message);
    }
}

// rotas necessárias:
// criar report de review
// pegar todos os reports
// pegar report por review
// pegar report por usuario
// pegar report por livro
// desabilitar report
// desabilitar review

