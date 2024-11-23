package com.example.KnowJob.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String category;
    private LocalDateTime createdAt;
    private Boolean isAnonymous;
    private Integer likeCount;
    private Integer dislikeCount;

    private String author;

}