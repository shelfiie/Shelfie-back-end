package com.mieker.ifpr.shelfie.dto;

import lombok.Data;

@Data
public class RegisterUserDTO {
    private String name;
    private String usernome;
    private String email;
    private String password;
}
