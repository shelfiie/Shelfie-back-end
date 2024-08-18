package com.mieker.ifpr.shelfie.repository;

import com.mieker.ifpr.shelfie.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {
    List<Like> findByReviewId(UUID reviewId);

    Like findByReviewIdAndUserId(UUID reviewId, UUID userId);
}
