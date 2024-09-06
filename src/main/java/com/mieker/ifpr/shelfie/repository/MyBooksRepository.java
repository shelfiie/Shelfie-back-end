package com.mieker.ifpr.shelfie.repository;

import com.mieker.ifpr.shelfie.entity.MyBooks;
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

//    @Query()
//    boolean findByBookIdAndUserId(UUID bookId, UUID userId);

    MyBooks findMyBooksByBookIdAndUserId(UUID bookId, UUID userId);

    List<MyBooks> findAllByUserIdAndBookStatus(UUID userId, BookStatus bookStatus);

    List<MyBooks> findByUserId(UUID id);

    List<MyBooks> findAllByUserIdAndEnabled(UUID userId, boolean b);

    List<MyBooks> findMyBooksByUserIdAndFavorite(UUID userId, boolean isFavorite);

    List<MyBooks> findMyBooksByBookIdAndFavorite(UUID bookId, boolean isFavorite);

    List<MyBooks> findMyBooksByFavorite(boolean isFavorite);

    int countMyBooksByUserIdAndBookStatusAndEnabled(UUID userId, BookStatus bookStatus, boolean b);

    int countMyBooksByUserIdAndFavoriteAndEnabled(UUID userId, boolean b, boolean b1);

    @Query("SELECT COUNT(r) FROM Review r JOIN r.myBooks mb WHERE mb.user.id = :userId AND r.enabled = true")
    int countReviewByUserId(UUID userId);

    MyBooks findMyBooksById(UUID myBooksId);
}
