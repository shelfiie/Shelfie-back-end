package com.mieker.ifpr.shelfie.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id", nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "my_books_id", nullable = false, updatable = false)
    private MyBooks myBooks;
    @Column(name = "review_rating")
    private float rating;
    @Column(name = "review_review")
    private String review;
    @Column(name = "review_enabled")
    private boolean enabled = true;
//    @UpdateTimestamp(source = SourceType.DB)
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "review_created_at", nullable = false, updatable = false)
    private Date createdAt;
}
