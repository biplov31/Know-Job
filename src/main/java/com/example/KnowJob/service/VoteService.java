package com.example.KnowJob.service;

import com.example.KnowJob.dto.VoteRequestDto;
import com.example.KnowJob.model.*;
import com.example.KnowJob.repository.CommentRepository;
import com.example.KnowJob.repository.PostRepository;
import com.example.KnowJob.repository.ReviewRepository;
import com.example.KnowJob.repository.VoteRepository;
import com.example.KnowJob.util.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final ReviewRepository reviewRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LoggedInUser loggedInUser;

    public void addVote(VoteRequestDto voteRequestDto) {
        User user = loggedInUser.getLoggedInUserEntity();

        Vote vote = Vote.builder()
                .voteType(VoteType.valueOf(voteRequestDto.getVoteType()))
                .build();

        if (voteRequestDto.getReviewId() != null) {
            Long reviewId = voteRequestDto.getReviewId();
            Vote existingVote = voteRepository.findByUserIdAndReviewId(user.getId(), reviewId).orElse(null);

            // if vote doesn't exist, create one
            // if user had previously cast a LIKE vote, they should still be able to update it with a DISLIKE vote
            if (existingVote == null || existingVote.getVoteType() != vote.getVoteType()) {
                Review review = reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new RuntimeException("Review not found."));
                vote.setReview(review);
                vote.setUser(user);
                voteRepository.save(vote);
            }

        } else if (voteRequestDto.getPostId() != null) {
            Long postId = voteRequestDto.getPostId();
            Vote existingVote = voteRepository.findByUserIdAndPostId(user.getId(), postId).orElse(null);

            if (existingVote == null || existingVote.getVoteType() != vote.getVoteType()) {
                Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new RuntimeException("Post not found."));
                vote.setPost(post);
                vote.setUser(user);
                voteRepository.save(vote);
            }

        } else if (voteRequestDto.getCommentId() != null) {
            Long commentId = voteRequestDto.getCommentId();
            Vote existingVote = voteRepository.findByUserIdAndCommentId(user.getId(), commentId).orElse(null);

            if (existingVote == null || existingVote.getVoteType() != vote.getVoteType()) {
                Comment comment = commentRepository.findById(commentId)
                        .orElseThrow(() -> new RuntimeException("Comment not found."));
                vote.setComment(comment);
                vote.setUser(user);
                voteRepository.save(vote);
            }
        }
    }

}
