package com.example.KnowJob.service;

import com.example.KnowJob.dto.CommentRequestDto;
import com.example.KnowJob.dto.CommentResponseDto;
import com.example.KnowJob.dto.CommentUpdateRequestDto;
import com.example.KnowJob.mapper.CommentMapper;
import com.example.KnowJob.model.Comment;
import com.example.KnowJob.model.Post;
import com.example.KnowJob.model.Review;
import com.example.KnowJob.model.User;
import com.example.KnowJob.repository.CommentRepository;
import com.example.KnowJob.repository.PostRepository;
import com.example.KnowJob.repository.ReviewRepository;
import com.example.KnowJob.util.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final LoggedInUser loggedInUser;
    private final ReviewRepository reviewRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    public CommentResponseDto addCommentToReview(Long reviewId, CommentRequestDto commentRequestDto) {
        Comment comment = commentMapper.map(commentRequestDto);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found."));
        comment.setReview(review);

        comment.setUser(loggedInUser.getLoggedInUserEntity());

        Comment savedComment = commentRepository.save(comment);
        if (savedComment.getIsAnonymous()) {
            savedComment.setUser(null);
        }
        return commentMapper.map(savedComment);
    }

    public CommentResponseDto addCommentToPost(Long postId, CommentRequestDto commentRequestDto) {
        Comment comment = commentMapper.map(commentRequestDto);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found."));
        comment.setPost(post);

        comment.setUser(loggedInUser.getLoggedInUserEntity());

        Comment savedComment = commentRepository.save(comment);
        if (savedComment.getIsAnonymous()) {
            savedComment.setUser(null);
        }
        return commentMapper.map(savedComment);
    }

    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found."));

        if (!isOriginalAuthor(comment)) {
            throw new RuntimeException("Not authorized to update comment.");
        }

        comment.setContent(commentUpdateRequestDto.getContent());
        comment.setIsAnonymous(commentUpdateRequestDto.getIsAnonymous());

        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.map(updatedComment);
    }

    public CommentResponseDto getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found."));

        return commentMapper.map(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found."));

        if (isOriginalAuthor(comment)) {
            commentRepository.deleteById(commentId);
            Review review = comment.getReview();
            review.removeComment(comment);
        }

        if (commentRepository.existsById(commentId)) {
            throw new RuntimeException("Failed to delete comment.");
        }
    }

    public boolean isOriginalAuthor(Comment existingComment) {
        User currentUser = loggedInUser.getLoggedInUserEntity();

        return existingComment.getUser().equals(currentUser);
    }

}
