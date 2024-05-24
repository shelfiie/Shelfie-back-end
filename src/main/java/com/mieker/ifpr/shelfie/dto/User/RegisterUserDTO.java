package com.mieker.ifpr.shelfie.dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterUserDTO {
    private String name;
    private String usernome;
    private String email;
    private String password;
}
