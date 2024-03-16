package com.mieker.ifpr.shelfie.entity;

import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_my_books")
public class MyBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "my_books_id", nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false, unique = true)
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false, updatable = false, unique = true)
    private Book book;
    @Enumerated(EnumType.STRING)
    @Column(name = "my_books_status", nullable = false)
    private BookStatus bookStatus;
    @Column(name = "my_books_enable", nullable = false)
    private boolean enabled = true;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "my_books_created_at", nullable = false, updatable = false)
    private Date createdAt;
}
