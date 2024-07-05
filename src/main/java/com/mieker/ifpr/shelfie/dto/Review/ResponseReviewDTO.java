package com.mieker.ifpr.shelfie.dto.Review;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ResponseReviewDTO {
    private UUID id;
    private UUID userId;
    private UUID bookId;
    private float rating;
    private String review;
    private Date createdAt;
}
