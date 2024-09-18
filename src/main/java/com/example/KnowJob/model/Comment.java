package com.example.KnowJob.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "like_count", nullable = false)
    private Integer likeCount = 0;

    @Column(name = "dislike_count", nullable = false)
    private Integer dislikeCount = 0;

    @Column(name = "is_anonymous", nullable = false)
    private Boolean isAnonymous = true;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

}
