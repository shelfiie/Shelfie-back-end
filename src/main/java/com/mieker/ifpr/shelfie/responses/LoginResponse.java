package com.mieker.ifpr.shelfie.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private long expiresIn;
}
