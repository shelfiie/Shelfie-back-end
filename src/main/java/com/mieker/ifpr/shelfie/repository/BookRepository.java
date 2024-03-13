package com.mieker.ifpr.shelfie.repository;

import com.mieker.ifpr.shelfie.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
