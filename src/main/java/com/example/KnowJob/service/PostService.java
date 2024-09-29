package com.example.KnowJob.service;

import com.example.KnowJob.dto.CommentResponseDto;
import com.example.KnowJob.dto.PostRequestDto;
import com.example.KnowJob.dto.PostResponseDto;
import com.example.KnowJob.dto.PostUpdateRequestDto;
import com.example.KnowJob.mapper.CommentMapper;
import com.example.KnowJob.mapper.PostMapper;
import com.example.KnowJob.model.Comment;
import com.example.KnowJob.model.Post;
import com.example.KnowJob.model.PostCategory;
import com.example.KnowJob.model.User;
import com.example.KnowJob.repository.PostRepository;
import com.example.KnowJob.util.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final LoggedInUser loggedInUser;

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = postMapper.map(postRequestDto);

        User user = loggedInUser.getLoggedInUserEntity();
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        if (savedPost.getId() != null) {
            return postMapper.map(savedPost);
        } else {
            throw new RuntimeException("Failed to save post.");
        }
    }

    public PostResponseDto updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found."));

        if (!isOriginalAuthor(existingPost)) {
            throw new SecurityException("Not authorized to update post.");
        }

        existingPost.setTitle(postUpdateRequestDto.getTitle());
        existingPost.setContent(postUpdateRequestDto.getContent());
        existingPost.setCategory(PostCategory.valueOf(postUpdateRequestDto.getCategory()));
        existingPost.setIsAnonymous(postUpdateRequestDto.getIsAnonymous());

        Post updatedPost = postRepository.save(existingPost);
        return postMapper.map(updatedPost);
    }

    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found."));

        return postMapper.map(post);
    }

    public List<PostResponseDto> getPosts() {
        List<Post> posts = postRepository.findAll();

        return posts
                .stream()
                .map(post -> postMapper.map(post))
                .toList();
    }

    public void deletePost(Long postId) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found."));

        User currentUser = loggedInUser.getLoggedInUserEntity();

        if (!existingPost.getUser().equals(currentUser)) {
            throw new SecurityException("Not authorized to delete post.");
        }

        postRepository.deleteById(postId);
        currentUser.removePost(existingPost);
    }

    public boolean isOriginalAuthor(Post existingPost) {
        User currentUser = loggedInUser.getLoggedInUserEntity();

        return existingPost.getUser().equals(currentUser);
    }

    public Set<CommentResponseDto> getComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found."));

        Set<Comment> comments = post.getComments();

        return comments
                .stream()
                .map(comment -> commentMapper.map(comment))
                .collect(Collectors.toSet());
    }

}
