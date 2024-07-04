package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.MyBooks.MyBooksDTO;
import com.mieker.ifpr.shelfie.dto.Review.ReviewDTO;
import com.mieker.ifpr.shelfie.service.ReadingProgressService;
import com.mieker.ifpr.shelfie.service.ReviewService;
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
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private final ReviewService reviewService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("{booksId}")
    public ResponseEntity<ReviewDTO> createReview(@PathVariable UUID booksId, @RequestBody ReviewDTO reviewDTO) throws ParseException {
        ReviewDTO responseReview = reviewService.create(booksId, reviewDTO);
        return ResponseEntity.ok(responseReview);
    }
}
