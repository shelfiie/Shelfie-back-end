package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.Book.FavoriteBookDTO;
import com.mieker.ifpr.shelfie.dto.Book.ListUserFavoriteBookDTO;
import com.mieker.ifpr.shelfie.dto.Review.ReviewDTO;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.Review;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.exception.NotFoundException;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import com.mieker.ifpr.shelfie.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FavoriteBookService {
    private final MyBooksRepository mbRepository;
    private final UserRepository userRepository;
    private Validation userValidation;

    public String favoriteBook(UUID bookId) {
        UUID userId = userValidation.userAuthenticator();
        MyBooks myBooks = mbRepository.findMyBooksByBookIdAndUserId(bookId, userId);
        String message;
        if (myBooks == null) {
            throw new NotFoundException("Para favoritar um livro é necessário adiciona-lo a sua biblioteca.");
        } else {
            if (myBooks.isFavorite()) {
                myBooks.setFavorite(false);
                mbRepository.save(myBooks);
                message = "Livro desfavoritado com sucesso!";
            } else {
                myBooks.setFavorite(true);
                mbRepository.save(myBooks);
                message = "Livro favoritado com sucesso!";
            }
        }
        return message;
    }


    public List<FavoriteBookDTO> getMyFavoriteBooks() {
        UUID userId = userValidation.userAuthenticator();
        return this.getFavoriteBooksByUserId(userId);
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

    public boolean isFavorited(UUID bookId) {
        UUID userId = userValidation.userAuthenticator();
        MyBooks myBooks = mbRepository.findMyBooksByBookIdAndUserId(bookId, userId);
        if (myBooks != null) {
            return myBooks.isFavorite();
        } else {
            return false;
        }
    }

    public List<ListUserFavoriteBookDTO> getFavoriteBooksByBookId(UUID bookId) {
        List<MyBooks> myBooksList = mbRepository.findMyBooksByBookIdAndFavorite(bookId, true);
        return myBooksList.stream().map(
                myBooks -> {
                    ListUserFavoriteBookDTO listUserDTO = new ListUserFavoriteBookDTO();
                    User user = userRepository.findById(myBooks.getUser().getId()).get();
                    listUserDTO.setUserId(user.getId());
                    listUserDTO.setName(user.getName());
                    listUserDTO.setUserImage(user.getImage());
                    listUserDTO.setBookId(bookId);
                    listUserDTO.setTitle(myBooks.getBook().getTitle());
                    return listUserDTO;
                }
        ).toList();
    }

    public List<ListUserFavoriteBookDTO> getAllFavoriteBooks() {
        List<MyBooks> myBooksList = mbRepository.findMyBooksByFavorite(true);
        return myBooksList.stream().map(
                myBooks -> {
                    ListUserFavoriteBookDTO listUserDTO = new ListUserFavoriteBookDTO();
                    User user = userRepository.findById(myBooks.getUser().getId()).get();
                    listUserDTO.setUserId(user.getId());
                    listUserDTO.setName(user.getName());
                    listUserDTO.setUserImage(user.getImage());
                    listUserDTO.setBookId(myBooks.getBook().getId());
                    listUserDTO.setTitle(myBooks.getBook().getTitle());
                    return listUserDTO;
                }
        ).toList();
    }
}
