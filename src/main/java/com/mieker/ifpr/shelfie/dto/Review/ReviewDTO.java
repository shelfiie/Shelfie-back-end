package com.mieker.ifpr.shelfie.dto.Review;

import com.mieker.ifpr.shelfie.entity.MyBooks;
import lombok.Data;

import java.util.UUID;

@Data
public class ReviewDTO {
    private float rating;
    private String review;
}
