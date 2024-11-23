package com.example.KnowJob.mapper;

import com.example.KnowJob.dto.CommentRequestDto;
import com.example.KnowJob.dto.CommentResponseDto;
import com.example.KnowJob.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment map(CommentRequestDto commentRequestDto) {
        return Comment.builder()
                .content(commentRequestDto.getContent())
                .isAnonymous(commentRequestDto.getIsAnonymous())
                .build();
    }

    public CommentResponseDto map(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .isAnonymous(comment.getIsAnonymous())
                .likeCount(comment.getLikeCount())
                .dislikeCount(comment.getDislikeCount())
                .build();
    }

}
