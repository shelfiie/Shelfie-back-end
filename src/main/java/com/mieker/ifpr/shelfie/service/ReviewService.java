package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.Book.BookDTO;
import com.mieker.ifpr.shelfie.dto.Review.ReviewDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.Review;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.exception.UserNotAssociatedException;
import com.mieker.ifpr.shelfie.mapper.ReviewMapper;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import com.mieker.ifpr.shelfie.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MyBooksRepository mbRepository;
    private final ReviewMapper rMapper;
    private Validation userValidation;

    public ReviewDTO createReview(UUID booksId, ReviewDTO reviewDTO) throws ParseException {
        UUID userId = userValidation.userAuthenticator();
        MyBooks myBooks = mbRepository.findMyBooksByBookIdAndUserId(booksId, userId);
        Review review = new Review();
        if (myBooks == null) {
            throw new UserNotAssociatedException("Não tem usuário associado ao livro");
        } else {
            if (myBooks.getBookStatus() == BookStatus.LIDO || myBooks.getBookStatus() == BookStatus.ABANDONADO) {
                review.setMyBooks(myBooks);
                review.setReview(reviewDTO.getReview());
                review.setRating(reviewDTO.getRating());
                reviewRepository.save(review);
            } else {
                throw new UserNotAssociatedException("O status do livro não está como LIDO ou ABANDONADO.");
            }
        }
//        TODO
//        ver o que retornar aqui
//        criar um novo DTO para um response com id do usuario e do livro?
        return rMapper.reviewToReviewDTO(review);

    }

}
