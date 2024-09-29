package com.example.KnowJob.mapper;

import com.example.KnowJob.dto.*;
import com.example.KnowJob.model.Post;
import com.example.KnowJob.model.PostCategory;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post map(PostRequestDto postRequestDto) {
        return Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .category(PostCategory.valueOf(postRequestDto.getCategory()))
                .isAnonymous(postRequestDto.getIsAnonymous())
                .build();
    }

    public PostResponseDto map(Post post) {
        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory().name())
                .isAnonymous(post.getIsAnonymous())
                .likeCount(post.getLikeCount())
                .dislikeCount(post.getDislikeCount())
                .build();
    }

}