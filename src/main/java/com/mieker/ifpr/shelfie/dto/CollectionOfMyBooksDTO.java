package com.mieker.ifpr.shelfie.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CollectionOfMyBooksDTO {
    private UUID bookId;
    private Integer page;
    private String commentary;
    private String createdAt;
}
