package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.Book.FavoriteBookDTO;
import com.mieker.ifpr.shelfie.dto.Review.ReviewDTO;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.Review;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.exception.NotFoundException;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FavoriteBookService {
    private final MyBooksRepository mbRepository;
    private Validation userValidation;

    public String favoriteBook(UUID bookId) {
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


    public List<FavoriteBookDTO> getMyFavoriteBooks() {
        UUID userId = userValidation.userAuthenticator();
        return this.getFavoriteBooksByUserId(userId);
    }

    public String unfavoriteBook(UUID bookId) {
        UUID userId = userValidation.userAuthenticator();
        MyBooks myBooks = mbRepository.findMyBooksByBookIdAndUserId(bookId, userId);
        if (myBooks == null) {
            throw new NotFoundException("Para desfavoritar um livro é necessário adiciona-lo a sua biblioteca.");
        } else {
            myBooks.setFavorite(false);
            mbRepository.save(myBooks);
        }
        return "Livro desfavoritado com sucesso!";
    }

    public List<FavoriteBookDTO> getFavoriteBooksByUserId(UUID userId) {
        List<MyBooks> myBooksList = mbRepository.findMyBooksByUserIdAndFavorite(userId, true);
        return myBooksList.stream().map(
                myBooks -> {
                    FavoriteBookDTO fbDTO = new FavoriteBookDTO();
                    fbDTO.setBookId(myBooks.getBook().getId());
                    fbDTO.setTitle(myBooks.getBook().getTitle());
                    return fbDTO;
                }
        ).toList();
    }
}
