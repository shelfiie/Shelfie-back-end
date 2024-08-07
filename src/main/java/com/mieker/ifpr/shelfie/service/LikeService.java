package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.Like.LikeDTO;
import com.mieker.ifpr.shelfie.dto.Like.UserLikeDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.Like;
import com.mieker.ifpr.shelfie.entity.Review;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.repository.LikeRepository;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import com.mieker.ifpr.shelfie.repository.ReviewRepository;
import com.mieker.ifpr.shelfie.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LikeService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private Validation userValidation;

    public String create(UUID reviewId) {
        UUID userId = userValidation.userAuthenticator();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
        Like like = new Like();
        like.setReview(review);
        like.setUser(user);
        likeRepository.save(like);
        return "Review curtida com sucesso!";
    }

    public List<LikeDTO> getReviewLikes(UUID reviewId) {
        List<Like> likeList = likeRepository.findByReviewId(reviewId);
        return likeList.stream().map(like -> {
            LikeDTO likeDTO = new LikeDTO();
            likeDTO.setUserId(like.getUser().getId());
            likeDTO.setNickname(like.getUser().getNickname());
            return likeDTO;
        }).toList();
    }

    public String delete(UUID reviewId) {
        UUID userId = userValidation.userAuthenticator();
        Optional<Like> like = likeRepository.findByReviewId(reviewId).stream().filter(l -> l.getUser().getId().equals(userId)).findFirst();
        like.ifPresent(likeRepository::delete);
        return "Review descurtida com sucesso!";
    }

    public List<UserLikeDTO> getUserLikes() {
        UUID userId = userValidation.userAuthenticator();
        List<Like> likeList = likeRepository.findAll();
        return likeList.stream().filter(like -> like.getUser().getId().equals(userId)).map(like -> {
            UserLikeDTO userLikeDTO = new UserLikeDTO();
            userLikeDTO.setReviewId(like.getReview().getId());
            userLikeDTO.setBookId(like.getReview().getMyBooks().getBook().getId());
            return userLikeDTO;
        }).toList();
    }
}
