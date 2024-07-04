package com.mieker.ifpr.shelfie.dto.Review;

import com.mieker.ifpr.shelfie.entity.MyBooks;
import lombok.Data;

import java.util.UUID;

@Data
public class ReviewDTO {
    private UUID id;
    private MyBooks myBooks;
    private float rating;
    private String review;
}
