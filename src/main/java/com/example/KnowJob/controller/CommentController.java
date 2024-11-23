package com.example.KnowJob.controller;

import com.example.KnowJob.dto.CommentRequestDto;
import com.example.KnowJob.dto.CommentResponseDto;
import com.example.KnowJob.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentResponseDto> addCommentToPost(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.addCommentToPost(postId, commentRequestDto);

        if (commentResponseDto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/review/{reviewId}")
    public ResponseEntity<CommentResponseDto> addCommentToReview(@PathVariable Long reviewId, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.addCommentToPost(reviewId, commentRequestDto);

        if (commentResponseDto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
