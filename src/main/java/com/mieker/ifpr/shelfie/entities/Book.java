package com.mieker.ifpr.shelfie.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id", nullable = false)
    private long id;
    @Column(name = "book_google_id", unique = true, nullable = false)
    private String googleId;
    @Column(name = "book_title", nullable = false)
    private String title;
    @Column(name = "book_isbn_13", unique = true, nullable = false)
    private String isbn13;
    @Column(name = "book_isbn_10", unique = true, nullable = false)
    private String isbn10;
    @Column(name = "book_created_at", nullable = false)
    private Date createdAt;

}
