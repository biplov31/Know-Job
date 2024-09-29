package com.example.KnowJob.controller;

import com.example.KnowJob.dto.CommentResponseDto;
import com.example.KnowJob.dto.ReviewRequestDto;
import com.example.KnowJob.dto.ReviewResponseDto;
import com.example.KnowJob.dto.ReviewUpdateRequestDto;
import com.example.KnowJob.model.Comment;
import com.example.KnowJob.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/company/{id}")
    public ResponseEntity<ReviewResponseDto> addReview(@PathVariable Long companyId, @RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto reviewResponseDto = reviewService.addReview(companyId, reviewRequestDto);

        if (reviewResponseDto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long id, @RequestBody ReviewUpdateRequestDto reviewRequestDto) {
        ReviewResponseDto reviewResponseDto = reviewService.updateReview(id, reviewRequestDto);

        if (reviewResponseDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable Long id) {
        ReviewResponseDto reviewResponseDto = reviewService.getReview(id);

        if (reviewResponseDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<Set<CommentResponseDto>> getComments(@PathVariable Long id) {
        Set<CommentResponseDto> comments = reviewService.getComments(id);

        if (comments != null) {
            return ResponseEntity.status(HttpStatus.OK).body(comments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
