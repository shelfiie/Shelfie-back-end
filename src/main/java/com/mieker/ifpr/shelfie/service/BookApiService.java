package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.Book.BookDTO;
import com.mieker.ifpr.shelfie.responses.BookApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@AllArgsConstructor
// é uma biblioteca do Java que automatiza a geração de código boilerplate
public class BookApiService {

    private static final String GOOGLE_API_URL = "https://www.googleapis.com/books/v1/volumes/";

    public BookDTO getBookByGoogleId(String googleId) {
        String uri = "/" + googleId;
        BookApiResponse response = this.fetchBookDataFromAPI(uri);

        assert response != null;
        return this.setBook(response);
    }

    private BookApiResponse fetchBookDataFromAPI(String uri) {
        return WebClient
                .create(GOOGLE_API_URL)
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(BookApiResponse.class)
                .block();
    }

    public BookDTO setBook(BookApiResponse response) {
        BookDTO book = new BookDTO();
        book.setGoogleId(response.getId());
        book.setTitle(response.getVolumeInfo().getTitle());
        book.setPages(response.getVolumeInfo().getPageCount());
        setIsbnIdentifiers(book, response.getVolumeInfo().getIndustryIdentifiers());
        return book;
    }

    private void setIsbnIdentifiers(BookDTO book, List<BookApiResponse.VolumeInfo.IndustryIdentifier> identifiers) {
        if (identifiers != null) {
            for (BookApiResponse.VolumeInfo.IndustryIdentifier identifier : identifiers) {
                switch (identifier.getType()) {
                    case "ISBN_13":
                        book.setIsbn13(identifier.getIdentifier());
                        break;
                    case "ISBN_10":
                        book.setIsbn10(identifier.getIdentifier());
                        break;
                }
            }
        }
    }

    
}

