package com.mieker.ifpr.shelfie.dto;

import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class MyBookDTO {
    private UUID userId;
    private UUID googleId;
    private BookStatus bookStatus;
}
