package com.mieker.ifpr.shelfie.dto.Like;

import lombok.Data;

import java.util.UUID;

@Data
public class UserLikeDTO {
    private UUID bookId;
    private UUID reviewId;
}
