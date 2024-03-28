package com.mieker.ifpr.shelfie.repository;

import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MyBooksRepository extends JpaRepository<MyBooks, UUID> {
    Optional<MyBooks> findById(UUID id);

}
