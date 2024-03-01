package com.mieker.ifpr.shelfie.entities;

import java.util.Date;

public class Review {
    private Long id;
    private MyBooks myBooks;
    private float rating;
    private String review;
    private boolean enabled;
    private Date createdAt;
}
