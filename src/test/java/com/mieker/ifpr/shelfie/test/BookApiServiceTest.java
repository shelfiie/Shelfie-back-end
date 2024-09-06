package com.mieker.ifpr.shelfie.test;

import com.mieker.ifpr.shelfie.dto.Book.BookDTO;
import com.mieker.ifpr.shelfie.responses.BookApiResponse;
import com.mieker.ifpr.shelfie.service.BookApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BookApiServiceTest {

    @InjectMocks
    private BookApiService bookApiService;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBookByGoogleId_Success() {
        // Arrange
        String googleId = "uzu3DAAAQBAJ";
        BookApiResponse mockResponse = new BookApiResponse();
        mockResponse.setId(googleId);
        BookApiResponse.VolumeInfo volumeInfo = new BookApiResponse.VolumeInfo();
        volumeInfo.setTitle("Dama da meia-noite - Os artifícios das trevas - vol. 1");
        volumeInfo.setPageCount(560);
        mockResponse.setVolumeInfo(volumeInfo);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(BookApiResponse.class)).thenReturn(Mono.just(mockResponse));

        // Act
        BookDTO result = bookApiService.getBookByGoogleId(googleId);

        // Assert
        assertNotNull(result);
        assertEquals(googleId, result.getGoogleId());
        assertEquals("Dama da meia-noite - Os artifícios das trevas - vol. 1", result.getTitle());
        assertEquals(560, result.getPages());
    }

    @Test
    public void testGetBookByGoogleId_NotFound() {
        // Arrange
        String googleId = "invalidGoogleId";

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(BookApiResponse.class)).thenReturn(Mono.error(new WebClientResponseException(404, "Not Found", null, null, null)));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookApiService.getBookByGoogleId(googleId));
    }
}
