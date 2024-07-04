package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.Book.BookDTO;
import com.mieker.ifpr.shelfie.dto.Review.ReviewDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import com.mieker.ifpr.shelfie.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MyBooksRepository mbRepository;
    private Validation userValidation;

//    public ReviewService(ReviewRepository reviewRepository, MyBooksRepository mbRepository) {
//        this.reviewRepository = reviewRepository;
//        this.mbRepository = mbRepository;
//    }

//    TODO
//    fazer o create
    public ReviewDTO createReview(UUID booksId, ReviewDTO reviewDTO) throws ParseException {
        UUID userId = userValidation.userAuthenticator();
        ReviewDTO review = new ReviewDTO();
        if (mbRepository.findByBookIdAndUserId(booksId, userId)) {
            return review;
        }
        return review;

//        UUID userId = userValidation.userAuthenticator();
//        Optional<Book> optionalBook = bookRepository.findByGoogleId(googleId);
//        MyBooks myBook = optionalBook.isPresent() ? addBookToUser(optionalBook.get().getId(), userId, bookStatus) : createBookAndAddToUser(googleId, userId, bookStatus);
//        return myBooksMapper.myBookToMyBookDTO(myBook);
    }

}
