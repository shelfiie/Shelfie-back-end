package com.mieker.ifpr.shelfie.dto;

import com.mieker.ifpr.shelfie.entity.enumeration.UserRoles;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String name;
    private String usernome;
    private String email;
    private String image;
    private boolean enabled;
    private UserRoles role;
    private Date createdAt;
}
