package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.Book.BookDTO;
import com.mieker.ifpr.shelfie.mapper.BookApiMapper;
import com.mieker.ifpr.shelfie.responses.BookApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
// é uma biblioteca do Java que automatiza a geração de código boilerplate
public class BookApiService {

    private final BookApiMapper bookApiMapper;

    public BookDTO getBookByGoogleId(String googleId) {
        String url = "https://www.googleapis.com/books/v1/volumes";
        String uri = "/" + googleId;

        BookApiResponse response = WebClient
                .create(url)
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(BookApiResponse.class)
                .block();

        assert response != null;
        return BookApiMapper.mapApiToDtoGoogleId(response);
    }
}

