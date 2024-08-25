package com.mieker.ifpr.shelfie.entity;

import com.mieker.ifpr.shelfie.entity.enumeration.BookBadge;
import com.mieker.ifpr.shelfie.entity.enumeration.PaginometerBadge;
import com.mieker.ifpr.shelfie.entity.enumeration.ReadingProgressionBadge;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_user_badge")
public class UserBadge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_badge_id", nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name="user_id", nullable=false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="badge_id", nullable=false, updatable = false)
    private Badge badge;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_badge_paginometer", nullable = false)
    private PaginometerBadge paginometerBadge;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_badge_book", nullable = false)
    private BookBadge bookBadge;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_badge_read_progression", nullable = false)
    private ReadingProgressionBadge readingProgressionBadge;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "user_badge_updated_at", nullable = false, updatable = false)
    private Date updatedAt;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "user_badge_created_at", nullable = false, updatable = false)
    private Date createdAt;
}
