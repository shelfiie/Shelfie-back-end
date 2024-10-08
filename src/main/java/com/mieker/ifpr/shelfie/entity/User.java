package com.mieker.ifpr.shelfie.entity;

import com.mieker.ifpr.shelfie.entity.enumeration.BookBadge;
import com.mieker.ifpr.shelfie.entity.enumeration.PaginometerBadge;
import com.mieker.ifpr.shelfie.entity.enumeration.ReviewBadge;
import com.mieker.ifpr.shelfie.entity.enumeration.UserRoles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "tb_user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private UUID id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_nickname", unique = true, nullable = false)
    private String nickname;

    @Column(name = "user_password", nullable = false, updatable = false)
    private String password;

    @Column(name = "user_email", unique = true, nullable = false, updatable = false)
    private String email;

    @Column(name = "user_image")
    private String image;

    @Column(name = "user_enabled", nullable = false)
    private Boolean enabled = true;
//    rota para ativar e rota para desativar

    @Enumerated(EnumType.STRING)
    @Column(name = "user_badge_paginometer")
    private PaginometerBadge paginometerBadge;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_badge_book")
    private BookBadge bookBadge;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_badge_review")
    private ReviewBadge reviewBadge;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRoles role;

//    faz o timestamp na hora que for criado o usuário, sem precisar ser passado
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "user_created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());

        return List.of(authority);
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
