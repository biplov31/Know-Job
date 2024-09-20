package com.example.KnowJob.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDto {

    private String content;
    private Float rating;
    private String email;
    private String department;
    private Boolean isAnonymous;
    private Integer likeCount;
    private Integer dislikeCount;

}
