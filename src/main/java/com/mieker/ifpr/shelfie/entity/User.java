package com.mieker.ifpr.shelfie.entity;

import com.mieker.ifpr.shelfie.entity.enumeration.UserRoles;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private UUID id;
    @Column(name = "user_name", nullable = false)
    private String name;
    @Column(name = "user_username", unique = true, nullable = false)
    private String userName;
    @Getter
    @Column(name = "user_password", nullable = false, updatable = false)
    private String password;
    @Column(name = "user_email", unique = true, nullable = false, updatable = false)
    private String email;
    @Column(name = "user_image")
    private String image;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, updatable = false)
    private UserRoles role = UserRoles.ROLE_READER;
//    faz o timestamp na hora que for criado o usuário, sem precisar ser passado
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "user_created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
