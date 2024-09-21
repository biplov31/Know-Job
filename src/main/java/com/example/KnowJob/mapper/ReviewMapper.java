package com.example.KnowJob.mapper;

import com.example.KnowJob.dto.ReviewRequestDto;
import com.example.KnowJob.dto.ReviewResponseDto;
import com.example.KnowJob.model.Department;
import com.example.KnowJob.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review map(ReviewRequestDto reviewRequestDto) {
        return Review.builder()
                .email(reviewRequestDto.getEmail())
                .content(reviewRequestDto.getContent())
                .rating(reviewRequestDto.getRating())
                .department(Department.valueOf(reviewRequestDto.getDepartment()))
                .isAnonymous(reviewRequestDto.getIsAnonymous())
                .likeCount(reviewRequestDto.getLikeCount())
                .dislikeCount(reviewRequestDto.getDislikeCount())
                .build();
    }

    public ReviewResponseDto map(Review review) {
        return ReviewResponseDto.builder()
                .email(review.getEmail())
                .content(review.getContent())
                .rating(review.getRating())
                .department(review.getDepartment().name())
                .isAnonymous(review.getIsAnonymous())
                .likeCount(review.getLikeCount())
                .dislikeCount(review.getDislikeCount())
                .build();
    }

}
