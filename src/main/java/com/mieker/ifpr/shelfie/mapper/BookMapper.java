package com.mieker.ifpr.shelfie.mapper;

import com.mieker.ifpr.shelfie.dto.BookApiDTO;
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

    public Book bookDTOtoBook(BookApiDTO bookDTO) throws ParseException {
        Book book = new Book();
        book.setGoogleId(bookDTO.getGoogleId());
        book.setTitle(bookDTO.getTitle());
        book.setIsbn13(bookDTO.getIsbn13());
        book.setIsbn10(bookDTO.getIsbn10());
        return book;
//        olhar aqui
//        o mapper.map ta dando erro de conversão?
//        return mapper.map(bookDTO, Book.class);
    }
}
