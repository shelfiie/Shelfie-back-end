package com.mieker.ifpr.shelfie.mapper;

import com.mieker.ifpr.shelfie.dto.BookApiDTO;
import com.mieker.ifpr.shelfie.responses.BookApiResponse;
import org.springframework.stereotype.Component;

@Component
public class BookApiMapper {
//    mapeia os dados que retornam de uma consulta a api por isbn
    public static BookApiDTO mapApiToDto(BookApiResponse response) {
        BookApiResponse.Item item = response.getItems().get(0); // Supondo que haja apenas um item retornado
        BookApiDTO book = new BookApiDTO();
        book.setGoogleId(item.getGoogleId());
        System.out.println(item.getVolumeInfo().getTitle());
        book.setTitle(item.getVolumeInfo().getTitle());
        if (item.getVolumeInfo().getIndustryIdentifiers() != null) {
            for (BookApiResponse.Item.VolumeInfo.IndustryIdentifier identifier : item.getVolumeInfo().getIndustryIdentifiers()) {
                if ("ISBN_13".equals(identifier.getType())) {
                    book.setIsbn13(identifier.getIdentifier());
                } else if ("ISBN_10".equals(identifier.getType())) {
                    book.setIsbn10(identifier.getIdentifier());
                }
            }
        }

        return book;
    }
}
