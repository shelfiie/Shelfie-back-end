package com.mieker.ifpr.shelfie.mapper;

import com.mieker.ifpr.shelfie.dto.BookApiDTO;
import com.mieker.ifpr.shelfie.responses.BookApiResponse;
import org.springframework.stereotype.Component;

@Component
public class BookApiMapper {
////    mapeia os dados que retornam de uma consulta a api pelo id do goole
    public static BookApiDTO mapApiToDtoGoogleId(BookApiResponse response) {
        BookApiDTO book = new BookApiDTO();
        book.setGoogleId(response.getId());
        System.out.println(response.getId());
        book.setTitle(response.getVolumeInfo().getTitle());
        if (response.getVolumeInfo().getIndustryIdentifiers() != null) {
            for (BookApiResponse.VolumeInfo.IndustryIdentifier identifier : response.getVolumeInfo().getIndustryIdentifiers()) {
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
