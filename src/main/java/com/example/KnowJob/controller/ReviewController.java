package com.example.KnowJob.controller;

import com.example.KnowJob.dto.*;
import com.example.KnowJob.model.Comment;
import com.example.KnowJob.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/company/{companyId}")
    public ResponseEntity<ReviewResponseDto> addReview(@PathVariable Long companyId, @RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto reviewResponseDto = reviewService.addReview(companyId, reviewRequestDto);

        if (reviewResponseDto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long reviewId, @RequestBody ReviewUpdateRequestDto reviewRequestDto) {
        ReviewResponseDto reviewResponseDto = reviewService.updateReview(reviewId, reviewRequestDto);

        if (reviewResponseDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable Long reviewId) {
        ReviewResponseDto reviewResponseDto = reviewService.getReview(reviewId);

        if (reviewResponseDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getReviews() {
        List<ReviewResponseDto> reviewResponseDtos = reviewService.getReviews();

        if (reviewResponseDtos != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{reviewId}/comment")
    public ResponseEntity<?> addComment(@PathVariable Long reviewId, @RequestBody CommentRequestDto commentRequestDto) {
        System.out.println("Review comment received.");
        reviewService.addComment(reviewId, commentRequestDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{reviewId}/comment")
    public ResponseEntity<Set<CommentResponseDto>> getComments(@PathVariable Long reviewId) {
        Set<CommentResponseDto> comments = reviewService.getComments(reviewId);

        if (comments != null) {
            return ResponseEntity.status(HttpStatus.OK).body(comments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
