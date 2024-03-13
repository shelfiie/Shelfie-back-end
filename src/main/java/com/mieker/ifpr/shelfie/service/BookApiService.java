package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.BookApiDTO;
import com.mieker.ifpr.shelfie.mapper.BookApiMapper;
import com.mieker.ifpr.shelfie.response.BookApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@AllArgsConstructor
// é uma biblioteca do Java que automatiza a geração de código boilerplate
public class BookApiService {

    private final BookApiMapper bookApiMapper;

    public BookApiDTO getBookByISBN10(String isbn10) {
        String url = "https://www.googleapis.com/books/v1/volumes";
        String uri = "?q=isbn:" + isbn10;

        BookApiResponse response = WebClient
                .create(url)
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(BookApiResponse.class)
                .block();

        assert response != null;
        return BookApiMapper.mapApiToDto(response);
    }
}

