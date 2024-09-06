package com.mieker.ifpr.shelfie.dto.Book;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BookDTO {
    private UUID bookId;
    private String googleId;
    private String title;
    private String authors;
    private String smallThumbnailUrl;
    private String thumbnailUrl;
    private String description;
    private Integer pages;
    private String isbn13;
    private String isbn10;
}

