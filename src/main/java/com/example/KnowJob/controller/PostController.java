package com.example.KnowJob.controller;

import com.example.KnowJob.dto.CommentResponseDto;
import com.example.KnowJob.dto.PostRequestDto;
import com.example.KnowJob.dto.PostResponseDto;
import com.example.KnowJob.dto.PostUpdateRequestDto;
import com.example.KnowJob.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto postResponseDto = postService.createPost(postRequestDto);

        if (postRequestDto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDto);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        PostResponseDto postResponseDto = postService.updatePost(postId, postUpdateRequestDto);

        if (postResponseDto != null) {
            return ResponseEntity.ok().body(postResponseDto);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        List<PostResponseDto> postResponseDtos = postService.getPosts();

        if (postResponseDtos != null) {
            return ResponseEntity.ok().body(postResponseDtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.getPost(postId);

        if (postResponseDto != null) {
            return ResponseEntity.ok().body(postResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<Set<CommentResponseDto>> getComments(@PathVariable Long postId) {
        Set<CommentResponseDto> commentResponseDtos = postService.getComments(postId);

        if (commentResponseDtos != null) {
            return ResponseEntity.ok().body(commentResponseDtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
