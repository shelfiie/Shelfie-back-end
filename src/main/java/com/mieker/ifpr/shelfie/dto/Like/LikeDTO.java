package com.mieker.ifpr.shelfie.dto.Like;

import lombok.Data;

import java.util.UUID;

@Data
public class LikeDTO {
    private UUID userId;
    private String nickname;
}
