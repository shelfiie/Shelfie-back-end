package com.mieker.ifpr.shelfie.dto.Review;

import com.mieker.ifpr.shelfie.entity.MyBooks;

import java.util.UUID;

public class ReviewDTO {
    private UUID id;
    private MyBooks myBooks;
    private float rating;
    private String review;

}
