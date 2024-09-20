package com.example.KnowJob.service;

import com.example.KnowJob.dto.ReviewRequestDto;
import com.example.KnowJob.dto.ReviewResponseDto;
import com.example.KnowJob.mapper.ReviewMapper;
import com.example.KnowJob.model.Review;
import com.example.KnowJob.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewResponseDto addReview(ReviewRequestDto reviewRequestDto) {
        Review review = reviewMapper.map(reviewRequestDto);

        Review savedReview = reviewRepository.save(review);
        if (savedReview.getId() != null) {
            return reviewMapper.map(savedReview);
        } else {
            throw new RuntimeException("Failed to add review.");
        }


    }

}
