package com.mieker.ifpr.shelfie.dto;

import com.mieker.ifpr.shelfie.entity.MyBooks;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.UUID;

@Data
public class ReadingProgressDTO {
    private Integer page;
    private UUID myBooksId;
    private String commentary;
}
