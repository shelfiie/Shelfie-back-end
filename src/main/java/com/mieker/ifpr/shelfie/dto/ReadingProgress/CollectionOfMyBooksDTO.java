package com.mieker.ifpr.shelfie.dto.ReadingProgress;

import lombok.Data;

import java.util.UUID;

@Data
public class CollectionOfMyBooksDTO {
    private UUID id;
    private UUID bookId;
    private String googleId;
    private Integer page;
    private String commentary;
    private String createdAt;
}
