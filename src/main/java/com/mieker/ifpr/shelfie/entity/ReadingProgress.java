package com.mieker.ifpr.shelfie.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_reading_progress")
public class ReadingProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reading_progress_id", nullable = false, updatable = false)
    private UUID id;
    @Column(name = "reading_progress_page", nullable = false, updatable = false)
    private Integer page;
    @ManyToOne
    @JoinColumn(name = "my_books_id", nullable = false, updatable = false)
    private MyBooks myBooks;
    @Column(name = "reading_progress_commentary")
    private String commentary;
    @Column(name = "reading_progress_enabled", nullable = false)
    private Boolean enabled = true;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "reading_progress_created_at", nullable = false, updatable = false)
    private LocalDate createdAt;
}
