package com.mieker.ifpr.shelfie.dto.Book;

import lombok.Data;

@Data
public class BookDTO {
    private String googleId;
    private String title;
    private Integer pages;
    private String isbn13;
    private String isbn10;
}

