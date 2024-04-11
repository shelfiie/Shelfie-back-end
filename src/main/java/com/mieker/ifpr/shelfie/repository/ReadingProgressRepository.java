package com.mieker.ifpr.shelfie.repository;

import com.mieker.ifpr.shelfie.entity.ReadingProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReadingProgressRepository extends JpaRepository<ReadingProgress, UUID>  {
    Optional<ReadingProgress> findById(UUID id);

}
