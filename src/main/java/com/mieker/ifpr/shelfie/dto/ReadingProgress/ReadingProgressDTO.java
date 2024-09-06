package com.mieker.ifpr.shelfie.dto.ReadingProgress;

import lombok.Data;

import java.util.UUID;

@Data
public class ReadingProgressDTO {
    private Integer page;
    private UUID bookId;
    private String commentary;
}
