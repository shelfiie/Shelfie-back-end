package com.mieker.ifpr.shelfie.repository;

import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MyBooksRepository extends JpaRepository<MyBooks, UUID> {
    Optional<MyBooks> findById(UUID id);

    @Query("SELECT mb FROM MyBooks mb WHERE mb.user.id = :id AND mb.enabled = true")
    List<MyBooks> findAllByUserIdAndEnabledTrue(UUID id);

    List<MyBooks> findAllByBookId(UUID bookId);

    boolean findByBookIdAndUserId(UUID bookId, UUID userId);

    List<MyBooks> findAllByUserIdAndBookStatus(UUID userId, BookStatus bookStatus);

    List<MyBooks> findByUserId(UUID id);
}
