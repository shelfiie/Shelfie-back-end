package com.mieker.ifpr.shelfie.entity;

import com.mieker.ifpr.shelfie.entity.enumeration.UserRoles;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tb_roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role_user", nullable = false)
    private UserRoles role;

    @Column(name = "role_created_at", nullable = false)
    private Date createdAt;
}
