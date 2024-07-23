package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.Book.BookDTO;
import com.mieker.ifpr.shelfie.responses.BookApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
// é uma biblioteca do Java que automatiza a geração de código boilerplate
public class BookApiService {

    private static final String GOOGLE_API_URL = "https://www.googleapis.com/books/v1/volumes/";

    public BookDTO getBookByGoogleId(String googleId) {
        String uri = "/" + googleId;
        BookApiResponse response = this.fetchBookDataFromAPI(uri);
        System.out.println(response);
        assert response != null;
        System.out.println(this.setBook(response));
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
        String author = response.getVolumeInfo().getAuthors() != null ? String.join(", ", response.getVolumeInfo().getAuthors()) : "";
        System.out.println(author);
        book.setAuthors(author);
//        System.out.println(response.getVolumeInfo().getAuthors());
//        book.setSmallThumbnailUrl(response.getVolumeInfo().getImageLinks().getSmallThumbnail());
//        book.setThumbnailUrl(response.getVolumeInfo().getImageLinks().getThumbnail());
        book.setSmallThumbnailUrl(response.getVolumeInfo().getImageLinks().getSmallThumbnail() != null
                ? response.getVolumeInfo().getImageLinks().getSmallThumbnail()
                : "https://centrodametropole.fflch.usp.br/sites/centrodametropole.fflch.usp.br/files/user_files/livros/imagem/capa-no-book-cover.png");
        book.setThumbnailUrl(response.getVolumeInfo().getImageLinks().getThumbnail() != null
                ? response.getVolumeInfo().getImageLinks().getThumbnail()
                : "https://centrodametropole.fflch.usp.br/sites/centrodametropole.fflch.usp.br/files/user_files/livros/imagem/capa-no-book-cover.png");
        setIsbnIdentifiers(book, response.getVolumeInfo().getIndustryIdentifiers());
        return book;
    }

    private void setIsbnIdentifiers(BookDTO book, List<BookApiResponse.VolumeInfo.IndustryIdentifier> identifiers) {
        String isbn13 = "";
        String isbn10 = "";
        if (identifiers != null) {
            for (BookApiResponse.VolumeInfo.IndustryIdentifier identifier : identifiers) {
                switch (identifier.getType()) {
                    case "ISBN_13":
                        isbn13 = identifier.getIdentifier();
                        break;
                    case "ISBN_10":
                        isbn10 = identifier.getIdentifier();
                        break;
                }
            }
        }
        book.setIsbn13(isbn13);
        book.setIsbn10(isbn10);
    }

    
}

