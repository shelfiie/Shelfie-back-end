package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.Like.LikeDTO;
import com.mieker.ifpr.shelfie.dto.Like.UserLikeDTO;
import com.mieker.ifpr.shelfie.dto.MyBooks.MyBooksDTO;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.service.FavoriteBookService;
import com.mieker.ifpr.shelfie.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("{reviewId}")
    public ResponseEntity<String> likeReview(@PathVariable UUID reviewId) {
        String message = likeService.create(reviewId);
        return ResponseEntity.status(201).body(message);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("{reviewId}")
    public ResponseEntity<String> deslikeReview(@PathVariable UUID reviewId) {
        String message = likeService.delete(reviewId);
        return ResponseEntity.status(200).body(message);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("{reviewId}")
//    nessa da pra retornar o nome do usuario
    public ResponseEntity<List<LikeDTO>> getReviewLikes(@PathVariable UUID reviewId) {
        List<LikeDTO> likeDTOList = likeService.getReviewLikes(reviewId);
        return ResponseEntity.status(200).body(likeDTOList);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mine")
    public ResponseEntity<List<UserLikeDTO>> getUserLikes() {
        List<UserLikeDTO> userLikeDTOList = likeService.getUserLikes();
        return ResponseEntity.status(200).body(userLikeDTOList);
    }

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<UserLikeDTO>> getUserLikes(@PathVariable UUID reviewId) {
//        String message = likeService.create(reviewId);
//        return ResponseEntity.status(201).body(message);
//    }
}
