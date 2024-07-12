package com.mieker.ifpr.shelfie.dto.Book;

import lombok.Data;

// todo
// adicionar atributos que serão retornados na requisição
@Data
public class BookDTO {
    private String googleId;
    private String title;
//    private String authors;
//    private String imageUrl;
    private Integer pages;
    private String isbn13;
    private String isbn10;
}

