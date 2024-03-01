package com.mieker.ifpr.shelfie.entities;

import com.mieker.ifpr.shelfie.entities.enumeration.BookStatus;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_my_books")
public class MyBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "my_books_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @Enumerated(EnumType.STRING)
    @Column(name = "my_books_status", nullable = false)
    private BookStatus bookStatus;
    @Column(name = "my_books_created_at", nullable = false)
    private Date createdAt;
}
