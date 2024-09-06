package com.mieker.ifpr.shelfie.repository;

import com.mieker.ifpr.shelfie.entity.ReadingProgress;
import com.mieker.ifpr.shelfie.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
//    List<Review> findByBookId(UUID bookId);
    List<Review> findByMyBooksId(UUID myBookId);
}
