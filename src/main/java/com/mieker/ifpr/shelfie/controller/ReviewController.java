package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.Review.ReviewDTO;
import lombok.AllArgsConstructor;
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("{myBooksId}")
    public ResponseEntity<ReviewDTO> createReview(@PathVariable UUID myBooksId) throws ParseException {
        return new ReviewDTO();
    }
}
