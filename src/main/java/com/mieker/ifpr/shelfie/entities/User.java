package com.mieker.ifpr.shelfie.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;
    @Column(name = "user_name", nullable = false)
    private String name;
    @Column(name = "user_username", unique = true, nullable = false)
    private String username;
    @Column(name = "user_password", nullable = false)
    private String password;
    @Column(name = "user_email", unique = true, nullable = false)
    private String email;
    @Column(name = "user_image")
    private String image;
    @Column(name = "user_created_at", nullable = false)
    private Date createdAt;
}
