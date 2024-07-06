package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.MyBooks.MyBooksDTO;
import com.mieker.ifpr.shelfie.dto.Review.ResponseReviewDTO;
import com.mieker.ifpr.shelfie.dto.Review.ReviewDTO;
import com.mieker.ifpr.shelfie.service.ReadingProgressService;
import com.mieker.ifpr.shelfie.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.util.List;
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
        ReviewDTO responseReview = reviewService.createReview(booksId, reviewDTO);
//        return ResponseEntity.ok(responseReview);
        return ResponseEntity.status(201).body(responseReview);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ResponseReviewDTO>> getAllReviews() {
        List<ResponseReviewDTO> listResponseReview = reviewService.getAllReviews();
        return ResponseEntity.ok(listResponseReview);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("mine")
    public ResponseEntity<List<ResponseReviewDTO>> getMyReviews() {
        List<ResponseReviewDTO> responseReviewList = reviewService.getMyReviews();
        return ResponseEntity.ok(responseReviewList);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("review/{reviewId}")
    public ResponseEntity<ResponseReviewDTO> getReviewById(@PathVariable UUID reviewId) {
        ResponseReviewDTO responseReview = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(responseReview);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("book/{bookId}")
    public ResponseEntity<List<ResponseReviewDTO>> getReviewByBookId(@PathVariable UUID bookId) {
        List<ResponseReviewDTO> responseReviewList = reviewService.getReviewByBookId(bookId);
        return ResponseEntity.ok(responseReviewList);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("user/{userId}")
    public ResponseEntity<List<ResponseReviewDTO>> getReviewByUserId(@PathVariable UUID userId) {
        List<ResponseReviewDTO> responseReviewList = reviewService.getReviewByUserId(userId);
        return ResponseEntity.ok(responseReviewList);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable UUID reviewId, @RequestBody ReviewDTO reviewDTO) throws AccessDeniedException {
        ReviewDTO responseReview = reviewService.updateReview(reviewId, reviewDTO);
        return ResponseEntity.ok(responseReview);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable UUID reviewId) throws AccessDeniedException {
        String message = reviewService.deleteReview(reviewId);
        return ResponseEntity.status(204).body(message);
    }
}
