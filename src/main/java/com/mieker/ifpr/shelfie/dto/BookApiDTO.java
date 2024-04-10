package com.mieker.ifpr.shelfie.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class BookApiDTO {
    private String googleId;
    private String title;
    private Integer pages;
    private String isbn13;
    private String isbn10;
}

