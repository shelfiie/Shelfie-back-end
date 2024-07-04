package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.Book.BookDTO;
import com.mieker.ifpr.shelfie.dto.Review.ReviewDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.repository.ReviewRepository;

import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

//    TODO
//    fazer o create
    public Book createReview(UUID booksId, ReviewDTO reviewDTO) throws ParseException {

//        UUID userId = userValidation.userAuthenticator();
//        Optional<Book> optionalBook = bookRepository.findByGoogleId(googleId);
//        MyBooks myBook = optionalBook.isPresent() ? addBookToUser(optionalBook.get().getId(), userId, bookStatus) : createBookAndAddToUser(googleId, userId, bookStatus);
        return myBooksMapper.myBookToMyBookDTO(myBook);
    }

}
