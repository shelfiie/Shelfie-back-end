package com.mieker.ifpr.shelfie.entity;

import com.mieker.ifpr.shelfie.config.AuthorsListConverter;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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
    @Column(name = "book_author")
    private String authors;
    @Column(name = "book_title", nullable = false)
    private String title;
    @Column(name = "book_isbn_13", unique = true, nullable = false, updatable = false)
    private String isbn13;
    @Column(name = "book_isbn_10", unique = true, nullable = false, updatable = false)
    private String isbn10;
    @Column(name = "book_pages", nullable = false, updatable = false)
    private Integer pages;
    @Column(name = "book_description", updatable = false)
    private String description;
    @Column(name = "book_small_thumbnail_url")
    private String smallThumbnailUrl;
    @Column(name = "book_thumbnail_url")
    private String thumbnailUrl;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "book_created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

}
