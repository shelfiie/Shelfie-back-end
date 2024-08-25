package com.mieker.ifpr.shelfie.dto.Badge;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class BadgeDTO {
    private String image;
    private String description;
}
