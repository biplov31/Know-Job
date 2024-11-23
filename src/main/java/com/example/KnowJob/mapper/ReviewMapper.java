package com.example.KnowJob.mapper;

import com.example.KnowJob.dto.ReviewRequestDto;
import com.example.KnowJob.dto.ReviewResponseDto;
import com.example.KnowJob.dto.ReviewUpdateRequestDto;
import com.example.KnowJob.model.Department;
import com.example.KnowJob.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review map(ReviewRequestDto reviewRequestDto) {
        return Review.builder()
                .email(reviewRequestDto.getEmail())
                .title(reviewRequestDto.getTitle())
                .content(reviewRequestDto.getContent())
                .rating(reviewRequestDto.getRating())
                .department(Department.valueOf(reviewRequestDto.getDepartment().toUpperCase()))
                .isAnonymous(reviewRequestDto.getIsAnonymous())
                .build();
    }

    public Review map(ReviewUpdateRequestDto reviewUpdateRequestDto) {
        return Review.builder().build();
    }

    public ReviewResponseDto map(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .email(review.getEmail())
                .title(review.getTitle())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .rating(review.getRating())
                .department(review.getDepartment().name())
                .isAnonymous(review.getIsAnonymous())
                .likeCount(review.getLikeCount())
                .dislikeCount(review.getDislikeCount())
                .companyId(review.getCompany().getId())
                .companyName(review.getCompany().getName())
                .build();
    }

}
