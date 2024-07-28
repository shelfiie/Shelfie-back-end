package com.mieker.ifpr.shelfie.mapper;

import com.mieker.ifpr.shelfie.dto.Book.BookDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class BookMapper {
    private final ModelMapper mapper;

    @Autowired
    public BookMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Book bookDTOtoBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setGoogleId(bookDTO.getGoogleId());
        book.setTitle(bookDTO.getTitle());
        book.setPages(bookDTO.getPages());
        book.setIsbn13(bookDTO.getIsbn13());
        book.setIsbn10(bookDTO.getIsbn10());
        book.setAuthors(bookDTO.getAuthors());
        book.setDescription(bookDTO.getDescription());
        book.setSmallThumbnailUrl(bookDTO.getSmallThumbnailUrl());
        book.setThumbnailUrl(bookDTO.getThumbnailUrl());
        return book;
    }

    public BookDTO bookToBookDTO(Book book) {
        return mapper.map(book, BookDTO.class);
    }
}
