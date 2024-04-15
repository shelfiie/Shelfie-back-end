package com.mieker.ifpr.shelfie.dto;

import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateMyBooksDTO {
    private UUID id;
    private BookStatus bookStatus;
}
