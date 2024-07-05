package com.mieker.ifpr.shelfie.mapper;

import com.mieker.ifpr.shelfie.dto.Review.ResponseReviewDTO;
import com.mieker.ifpr.shelfie.dto.Review.ReviewDTO;
import com.mieker.ifpr.shelfie.dto.User.UpdateUserDTO;
import com.mieker.ifpr.shelfie.entity.Review;
import com.mieker.ifpr.shelfie.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewMapper {
    private final ModelMapper mapper;


    public ReviewMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ReviewDTO reviewToReviewDTO(Review review) {
        return this.mapper.map(review, ReviewDTO.class);
    }

    public ResponseReviewDTO reviewToResponseReviewDTO(Review reviews) {
        return this.mapper.map(reviews, ResponseReviewDTO.class);
    }
}
