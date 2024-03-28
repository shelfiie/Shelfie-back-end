package com.mieker.ifpr.shelfie.mapper;

import com.mieker.ifpr.shelfie.dto.MyBooksDTO;
import com.mieker.ifpr.shelfie.dto.UpdateMyBooksDTO;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MyBooksMapper {
    private final ModelMapper mapper;

    public MyBooksMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

//    todo
//    no mapper eu tenho que mapear que a string do googleId vai ser o googleId do book
//    e o user vai ser o uuid do user
    public MyBooks myBookDTOtoMyBook(MyBooksDTO myBooksDTO) {
//        MyBooks myBooks = new MyBooks();
//        Book book = new Book();
//        book.setGoogleId(myBooksDTO.getGoogleId());
        return mapper.map(myBooksDTO, MyBooks.class);
    }

    public MyBooksDTO myBookToMyBookDTO(MyBooks myBooks) {
        return mapper.map(myBooks, MyBooksDTO.class);
    }

//    TODO
//    refatorar o user disable lá
    public MyBooks updateMyBooksDisabled(MyBooks myBooksToUpdate) {
        myBooksToUpdate.setEnabled(false);
        mapper.map(myBooksToUpdate, MyBooksDTO.class);
        return myBooksToUpdate;
    }

    public MyBooks updateMyBooks(MyBooks myBooksToUpdate, UpdateMyBooksDTO updateMyBooksDTO) {
        mapper.map(updateMyBooksDTO, myBooksToUpdate);
        return myBooksToUpdate;
    }
}


//
//        book.setGoogleId(bookDTO.getGoogleId());
//        book.setTitle(bookDTO.getTitle());
//        book.setIsbn13(bookDTO.getIsbn13());
//        book.setIsbn10(bookDTO.getIsbn10());