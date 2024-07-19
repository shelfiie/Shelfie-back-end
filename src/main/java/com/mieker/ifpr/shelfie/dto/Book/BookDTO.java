package com.mieker.ifpr.shelfie.dto.Book;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;
import java.util.UUID;

// todo
// adicionar atributos que serão retornados na requisição
@Data
public class BookDTO {
//    private UUID id;
    private String googleId;
    private String title;
    private String authors;
    private String smallThumbnailUrl;
    private String thumbnailUrl;
    private Integer pages;
    private String isbn13;
    private String isbn10;
}

