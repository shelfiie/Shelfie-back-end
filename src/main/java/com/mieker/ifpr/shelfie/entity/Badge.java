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
@Table(name = "tb_badge")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "badge_id", nullable = false)
    private UUID id;

    @Column(name = "badge_name", nullable = false)
    private String name;

    @Column(name = "badge_image", nullable = false)
    private String image;

    @Column(name = "badge_description")
    private String description;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "badge_updated_at", nullable = false, updatable = false)
    private Date updatedAt;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "badge_created_at", nullable = false, updatable = false)
    private Date createdAt;
}
