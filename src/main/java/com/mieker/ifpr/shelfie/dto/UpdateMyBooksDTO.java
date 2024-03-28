package com.mieker.ifpr.shelfie.dto;

import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;

import java.util.UUID;

public class UpdateMyBooksDTO {
    private UUID id;
    private BookStatus bookStatus;
}
