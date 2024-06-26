package com.mieker.ifpr.shelfie.repository;

import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.ReadingProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadingProgressRepository extends JpaRepository<ReadingProgress, UUID>  {
    Optional<ReadingProgress> findById(UUID id);

    @Query("SELECT rp FROM ReadingProgress rp WHERE rp.myBooks.id = :myBooksId")
    List<ReadingProgress> findByMyBooksId(UUID myBooksId);
}
