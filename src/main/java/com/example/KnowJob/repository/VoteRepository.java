package com.example.KnowJob.repository;

import com.example.KnowJob.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    public boolean existsByUserIdAndReviewId(Long userId, Long reviewId);
    public boolean existsByUserIdAndPostId(Long userId, Long postId);
    public boolean existsByUserIdAndCommentId(Long userId, Long commentId);

    public Optional<Vote> findByUserIdAndReviewId(Long userId, Long reviewId);
    public Optional<Vote> findByUserIdAndPostId(Long userId, Long postId);
    public Optional<Vote> findByUserIdAndCommentId(Long userId, Long commentId);

}
