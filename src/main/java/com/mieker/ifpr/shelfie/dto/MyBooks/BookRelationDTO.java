package com.mieker.ifpr.shelfie.dto.MyBooks;

import lombok.Data;

@Data
public class BookRelationDTO {
    private int LENDO;
    private int LIDO;
    private int QUERO_LER;
    private int ABANDONADO;
    private int review;
    private int favorite;
    private Integer paginometer;
}
