package com.example.KnowJob.service;

import com.example.KnowJob.dto.*;
import com.example.KnowJob.mapper.CommentMapper;
import com.example.KnowJob.mapper.ReviewMapper;
import com.example.KnowJob.model.*;
import com.example.KnowJob.repository.CompanyRepository;
import com.example.KnowJob.repository.ReviewRepository;
import com.example.KnowJob.util.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyRepository companyRepository;
    private final ReviewMapper reviewMapper;
    private final CommentMapper commentMapper;
    private final LoggedInUser loggedInUser;

    public ReviewResponseDto addReview(Long companyId, ReviewRequestDto reviewRequestDto) {
        Review review = reviewMapper.map(reviewRequestDto);

        User user = loggedInUser.getLoggedInUserEntity();
        review.setUser(user);

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found."));
        review.setCompany(company);

        Review savedReview = reviewRepository.save(review);
        if (savedReview.getId() != null) {
            if (savedReview.getIsAnonymous()) {
                savedReview.setEmail(null);
            }
            return reviewMapper.map(savedReview);
        } else {
            throw new RuntimeException("Failed to add review.");
        }
    }

    public ReviewResponseDto updateReview(Long reviewId, ReviewUpdateRequestDto reviewUpdateRequestDto) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found."));

        if (!isOriginalAuthor(existingReview)) {
            throw new SecurityException("Not authorized to update review.");
        }

        existingReview.setContent(reviewUpdateRequestDto.getContent());
        existingReview.setDepartment(Department.valueOf(reviewUpdateRequestDto.getDepartment()));
        existingReview.setRating(reviewUpdateRequestDto.getRating());
        existingReview.setIsAnonymous(reviewUpdateRequestDto.getIsAnonymous());

        Review updatedReview = reviewRepository.save(existingReview);
        // JPA's save() method will either throw an exception on failure or return the saved entity.
        // There's no need to manually check the ID after saving.

        if (updatedReview.getIsAnonymous()) {
            updatedReview.setEmail(null);
        }
        return reviewMapper.map(updatedReview);
    }

    public ReviewResponseDto getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found."));

        if (review.getIsAnonymous()) {
            review.setEmail(null);
        }
        return reviewMapper.map(review);
    }

    public List<ReviewResponseDto> getReviews() {
        List<Review> reviews = reviewRepository.findAll();

        return reviews
                .stream()
                .map(review -> reviewMapper.map(review))
                .toList();
    }

    public void deleteReview(Long reviewId) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found."));

        if (!isOriginalAuthor(existingReview)) {
            throw new SecurityException("Not authorized to delete review.");
        }

        reviewRepository.deleteById(reviewId);
        if (reviewRepository.existsById(reviewId)) {
            throw new RuntimeException("Failed to delete review.");
        }
    }

    public boolean isOriginalAuthor(Review existingReview) {
        User currentUser = loggedInUser.getLoggedInUserEntity();

        return existingReview.getUser().equals(currentUser);
    }

    public void addComment(Long reviewId, CommentRequestDto commentRequestDto) {
        Comment comment = commentMapper.map(commentRequestDto);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found."));
        comment.setReview(review);

        comment.setUser(loggedInUser.getLoggedInUserEntity());

        review.addComment(comment);
        reviewRepository.save(review); // this will cascade and save the new comment
    }

    public Set<CommentResponseDto> getComments(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found."));

        Set<Comment> comments = review.getComments();
        for (Comment comment: comments) {
            System.out.println(comment);
        }
        return comments.stream()
                .map(comment -> commentMapper.map(comment))
                .collect(Collectors.toSet());
    }

}
