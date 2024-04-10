//package com.mieker.ifpr.shelfie.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.SourceType;
//
//import java.util.Date;
//import java.util.UUID;
//
//@Data
//@Entity
//@Table(name = "tb_badge")
//public class Badge {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "badge_id", nullable = false)
//    private UUID id;
//    @ManyToOne
//    @JoinColumn(name="user_id", nullable=false, updatable = false)
//    private User user;
//    @Column(name = "badge_name", nullable = false)
//    private String name;
//    @Column(name = "badge_image", nullable = false)
//    private String image;
//    @Column(name = "badge_description", nullable = false)
//    private String description;
//    @CreationTimestamp(source = SourceType.DB)
//    @Column(name = "badge_created_at", nullable = false, updatable = false)
//    private Date createdAt;
//}
