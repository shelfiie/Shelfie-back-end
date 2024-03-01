package com.mieker.ifpr.shelfie.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String image;
    private Date createdAt;


}
