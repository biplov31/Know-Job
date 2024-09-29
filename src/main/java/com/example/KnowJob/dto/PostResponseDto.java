package com.example.KnowJob.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private String title;
    private String content;
    private String category;
    private Boolean isAnonymous;
    private Integer likeCount;
    private Integer dislikeCount;

}