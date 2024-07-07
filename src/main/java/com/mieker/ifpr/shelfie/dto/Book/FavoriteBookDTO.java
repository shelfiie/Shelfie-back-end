package com.mieker.ifpr.shelfie.dto.Book;

import lombok.Data;

import java.util.UUID;

@Data
public class FavoriteBookDTO {
//    retornar foto também e nome
    private UUID bookId;
    private String title;

}
