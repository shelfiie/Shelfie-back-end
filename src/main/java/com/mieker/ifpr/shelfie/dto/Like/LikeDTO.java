package com.mieker.ifpr.shelfie.dto.Like;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class LikeDTO {
    private int quantity;
    private List<UserLikes> userLikes;

    @Data
    public static class UserLikes {
        private UUID userId;
        private String nickname;
    }
}
