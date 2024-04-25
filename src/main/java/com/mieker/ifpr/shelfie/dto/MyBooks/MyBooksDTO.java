package com.mieker.ifpr.shelfie.dto.MyBooks;

import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class MyBooksDTO {
    private UUID id;
    private UUID userId;
    private UUID bookId;
    private BookStatus bookStatus;
    private boolean enabled;
}
