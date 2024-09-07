package com.mieker.ifpr.shelfie.mapper;

import com.mieker.ifpr.shelfie.dto.MyBooks.MyBooksDTO;
import com.mieker.ifpr.shelfie.dto.MyBooks.MyBooksEnabledDTO;
import com.mieker.ifpr.shelfie.dto.MyBooks.UpdateMyBooksDTO;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MyBooksMapper {
    private final ModelMapper mapper;

    public MyBooksMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public MyBooks myBookDTOtoMyBook(MyBooksDTO myBooksDTO) {
        return mapper.map(myBooksDTO, MyBooks.class);
    }

    public MyBooksDTO myBookToMyBookDTO(MyBooks myBooks) {
        return mapper.map(myBooks, MyBooksDTO.class);
    }

    public MyBooksDTO updateMyBooksDisabled(MyBooks myBooksToUpdate) {
        return  mapper.map(myBooksToUpdate, MyBooksDTO.class);
    }

    public UpdateMyBooksDTO updateMyBooks(MyBooks myBooksToUpdate) {
        UpdateMyBooksDTO updateMyBooksDTO = mapper.map(myBooksToUpdate, UpdateMyBooksDTO.class);
        System.out.println("mapper" + updateMyBooksDTO);
        return updateMyBooksDTO;
    }

    public MyBooksEnabledDTO myBookToMyBookEnabledDTO(MyBooks myBooks) {
        return mapper.map(myBooks, MyBooksEnabledDTO.class);
    }
}


