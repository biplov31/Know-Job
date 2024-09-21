package com.example.KnowJob.controller;

import com.example.KnowJob.dto.ReviewRequestDto;
import com.example.KnowJob.dto.ReviewResponseDto;
import com.example.KnowJob.mapper.ReviewMapper;
import com.example.KnowJob.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> addReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto reviewResponseDto = reviewService.addReview(reviewRequestDto);

        if (reviewResponseDto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
