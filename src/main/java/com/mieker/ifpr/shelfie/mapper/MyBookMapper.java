package com.mieker.ifpr.shelfie.mapper;

import com.mieker.ifpr.shelfie.dto.MyBookDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MyBookMapper {
    private final ModelMapper mapper;

    public MyBookMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

//    todo
//    no mapper eu tenho que mapear que a string do googleId vai ser o googleId do book
//    e o user vai ser o uuid do user
    public MyBooks myBookDTOtoMyBook(MyBookDTO myBookDTO) {
//        MyBooks myBooks = new MyBooks();
//        Book book = new Book();
//        book.setGoogleId(myBookDTO.getGoogleId());
        return mapper.map(myBookDTO, MyBooks.class);
    }

    public MyBookDTO myBookToMyBookDTO(MyBooks myBooks) {
        return mapper.map(myBooks, MyBookDTO.class);
    }
}


//
//        book.setGoogleId(bookDTO.getGoogleId());
//        book.setTitle(bookDTO.getTitle());
//        book.setIsbn13(bookDTO.getIsbn13());
//        book.setIsbn10(bookDTO.getIsbn10());