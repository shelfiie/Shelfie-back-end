package com.mieker.ifpr.shelfie.dto;

import com.mieker.ifpr.shelfie.entity.MyBooks;
import lombok.Data;

import java.util.UUID;

@Data
public class CollectionOfMyBooksDTO {
    private String googleId;
    private Integer page;
    private String commentary;
    private String createdAt;
}
