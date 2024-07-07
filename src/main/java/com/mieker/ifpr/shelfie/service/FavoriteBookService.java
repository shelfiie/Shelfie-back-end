package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.Review.ReviewDTO;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.Review;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.exception.NotFoundException;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FavoriteBookService {
    private final MyBooksRepository mbRepository;
    private Validation userValidation;

    public String create(UUID bookId) {
        UUID userId = userValidation.userAuthenticator();
        MyBooks myBooks = mbRepository.findMyBooksByBookIdAndUserId(bookId, userId);
        if (myBooks == null) {
            throw new NotFoundException("Para favoritar um livro é necessário adiciona-lo a sua biblioteca.");
        } else {
            myBooks.setFavorite(true);
            mbRepository.save(myBooks);
        }
        return "Livro favoritado com sucesso!";
    }


}
