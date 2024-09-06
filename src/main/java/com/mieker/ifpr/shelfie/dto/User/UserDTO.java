package com.mieker.ifpr.shelfie.dto.User;

import com.mieker.ifpr.shelfie.entity.enumeration.BookBadge;
import com.mieker.ifpr.shelfie.entity.enumeration.PaginometerBadge;
import com.mieker.ifpr.shelfie.entity.enumeration.ReviewBadge;
import com.mieker.ifpr.shelfie.entity.enumeration.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {
    private UUID id;
    private String name;
    private String nickname;
    private String email;
    private String image;
    private Boolean enabled;
    private PaginometerBadge paginometerBadge;
    private ReviewBadge reviewBadge;
    private BookBadge bookBadge;
    private UserRoles role;
    private Date createdAt;
}
