package com.mieker.ifpr.shelfie.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "my_books_id", nullable = false)
    private MyBooks myBooks;
    @Column(name = "review_rating")
    private float rating;
    @Column(name = "review_review")
    private String review;
    @Column(name = "review_enabled")
    private boolean enabled;
    @Column(name = "review_created_at", nullable = false)
    private Date createdAt;
}
