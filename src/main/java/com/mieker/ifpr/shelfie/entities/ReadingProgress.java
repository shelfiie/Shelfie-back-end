package com.mieker.ifpr.shelfie.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_reading_progress")
public class ReadingProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reading_progress_id", nullable = false)
    private Long id;
    @Column(name = "reading_progress_page", nullable = false)
    private Integer page;
    @ManyToOne
    @JoinColumn(name = "my_books_id", nullable = false)
    private MyBooks myBooks;
    @Column(name = "reading_progress_commentary")
    private String commentary;
    @Column(name = "reading_progress_created_at", nullable = false)
    private Date createdAt;
}
