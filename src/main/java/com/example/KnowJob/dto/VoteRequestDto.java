package com.example.KnowJob.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteRequestDto {

    private String voteType;
    private Long reviewId;
    private Long postId;
    private Long commentId;

}
