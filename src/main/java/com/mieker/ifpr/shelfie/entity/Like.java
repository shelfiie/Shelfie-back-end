package com.mieker.ifpr.shelfie.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_like")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "like_id", nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false, updatable = false)
    private Review review;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "like_created_at", nullable = false, updatable = false)
    private Date createdAt;
}
