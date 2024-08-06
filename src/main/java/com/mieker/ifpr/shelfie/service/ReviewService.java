package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.Review.ResponseReviewDTO;
import com.mieker.ifpr.shelfie.dto.Review.ReviewDTO;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.Review;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.exception.IdNotFoundException;
import com.mieker.ifpr.shelfie.exception.NotFoundException;
import com.mieker.ifpr.shelfie.mapper.ReviewMapper;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import com.mieker.ifpr.shelfie.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MyBooksRepository mbRepository;
    private final ReviewMapper rMapper;
    private Validation userValidation;

    public ReviewDTO createReview(UUID booksId, ReviewDTO reviewDTO) {
        UUID userId = userValidation.userAuthenticator();
        MyBooks myBooks = mbRepository.findMyBooksByBookIdAndUserId(booksId, userId);
        Review review = new Review();
        if (myBooks == null) {
            throw new NotFoundException("Não tem usuário associado ao livro");
        } else {
            if (myBooks.getBookStatus() == BookStatus.LIDO || myBooks.getBookStatus() == BookStatus.ABANDONADO) {
                review.setMyBooks(myBooks);
                review.setReview(reviewDTO.getReview());
                review.setRating(reviewDTO.getRating());
                reviewRepository.save(review);
            } else {
                throw new NotFoundException("O status do livro não está como LIDO ou ABANDONADO.");
            }
        }
//        TODO
//        ver o que retornar aqui
//        criar um novo DTO para um response com id do usuario e do livro?
        return rMapper.reviewToReviewDTO(review);
    }

    public List<ResponseReviewDTO> getAllReviews() {
        List<Review> reviewList = reviewRepository.findAll();
        return reviewList.stream().map(
                review -> {
                    ResponseReviewDTO rrDTO = rMapper.reviewToResponseReviewDTO(review);
                    MyBooks mb = mbRepository.findById(review.getMyBooks().getId()).orElseThrow(() -> new RuntimeException("MyBooks not found with id: " + review.getMyBooks().getId()));
                    rrDTO.setBookId(mb.getBook().getId());
                    rrDTO.setUserId(mb.getUser().getId());
                    return rrDTO;
                }
        ).collect(Collectors.toList());
    }

    public ResponseReviewDTO getReviewById(UUID booksId) {
        Review review = reviewRepository.findById(booksId).orElseThrow(() -> new NotFoundException("Review not found with id: " + booksId));
        ResponseReviewDTO rrDTO = rMapper.reviewToResponseReviewDTO(review);
        rrDTO.setUserId(review.getMyBooks().getUser().getId());
        rrDTO.setBookId(review.getMyBooks().getBook().getId());
        return rrDTO;
    }

    public List<ResponseReviewDTO> getReviewByBookId(UUID bookId) {
        List<MyBooks> myBooksList = mbRepository.findAllByBookId(bookId);
        return this.getReviewList(bookId, myBooksList);
    }

    public List<ResponseReviewDTO> getReviewByUserId(UUID userId) {
        List<MyBooks> myBooksList = mbRepository.findAllByUserIdAndEnabled(userId, true);
        return this.getReviewList(userId, myBooksList);
    }

    public List<ResponseReviewDTO> getMyReviews() {
        UUID userId = userValidation.userAuthenticator();
        List<MyBooks> myBooksList = mbRepository.findAllByUserIdAndEnabled(userId, true);
        return this.getReviewList(userId, myBooksList);
    }

    private List<ResponseReviewDTO> getReviewList(UUID id, List<MyBooks> myBooksList) {
        List<ResponseReviewDTO> reviewList = new ArrayList<>();

        for (MyBooks myBooks : myBooksList){
            List<Review> rList = reviewRepository.findByMyBooksId(myBooks.getId());

            reviewList.addAll(rList.stream()
                    .map(review -> {
                        ResponseReviewDTO rrDTO = rMapper.reviewToResponseReviewDTO(review);
                        MyBooks mb = mbRepository.findById(review.getMyBooks().getId()).orElseThrow(() -> new RuntimeException("MyBooks not found with id: " + review.getMyBooks().getId()));
                        rrDTO.setBookId(mb.getBook().getId());
                        rrDTO.setUserId(mb.getUser().getId());
                        return rrDTO;
                    }).toList());
        }
        return reviewList;
    }

    public ReviewDTO updateReview(UUID reviewId, ReviewDTO reviewDTO) throws AccessDeniedException {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("Review com esse id não existe: " + reviewId));
        this.isUserAuthorized(review);
        review.setReview(reviewDTO.getReview());
        review.setRating(reviewDTO.getRating());
        reviewRepository.save(review);
        return rMapper.reviewToReviewDTO(review);
    }

    public String deleteReview(UUID reviewId) throws AccessDeniedException {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IdNotFoundException("Review com esse id não existe: " + reviewId));
        this.isUserAuthorized(review);
        reviewRepository.delete(review);
        return "Deletado com sucesso";
    }

    private void isUserAuthorized(Review review) throws AccessDeniedException {
        if (!review.getMyBooks().getUser().getId().equals(userValidation.userAuthenticator())) {
            throw new AccessDeniedException("You are not authorized to delete this review");
        }
    }
}
