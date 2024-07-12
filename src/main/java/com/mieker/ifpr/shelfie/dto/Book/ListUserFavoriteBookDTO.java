package com.mieker.ifpr.shelfie.dto.Book;

import lombok.Data;

import java.util.UUID;

@Data
public class ListUserFavoriteBookDTO {
//    TODO
//    depois ve se precisa separar
//    se não deixa assim mesmo
    private UUID userId;
    private String name;
    private String userImage;
    private UUID bookId;
    private String title;
}
