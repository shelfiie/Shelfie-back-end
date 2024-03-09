package com.mieker.ifpr.shelfie.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id", nullable = false)
    private UUID id;
    @Column(name = "book_google_id", unique = true, nullable = false, updatable = false)
    private String googleId;
    @Column(name = "book_title", nullable = false)
    private String title;
    @Column(name = "book_isbn_13", unique = true, nullable = false, updatable = false)
    private String isbn13;
    @Column(name = "book_isbn_10", unique = true, nullable = false, updatable = false)
    private String isbn10;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "book_created_at", nullable = false, updatable = false)
    private Date createdAt;

}
