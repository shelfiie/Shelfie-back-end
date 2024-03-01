package com.mieker.ifpr.shelfie.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_badge")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "badge_id", nullable = false)
    private long id;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @Column(name = "badge_name", nullable = false)
    private String name;
    @Column(name = "badge_image", nullable = false)
    private String image;
    @Column(name = "badge_description", nullable = false)
    private String description;
    @Column(name = "badge_created_at", nullable = false)
    private Date createdAt;
}
