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
public class ReviewResponseDto {

    private Long id;
    private String title;
    private String content;
    private Float rating;
    private String email;
    private LocalDateTime createdAt;
    private String department;
    private Boolean isAnonymous;
    private Integer likeCount;
    private Integer dislikeCount;

    private Long companyId;
    private String companyName;

}
