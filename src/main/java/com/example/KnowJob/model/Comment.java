package com.example.KnowJob.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

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
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "like_count", nullable = true)
    @Formula("(SELECT COUNT(v.id) FROM votes v WHERE v.comment_id = id AND v.vote_type = 'LIKE')")
    private Integer likeCount;

    @Column(name = "dislike_count", nullable = true)
    @Formula("(SELECT COUNT(v.id) FROM votes v WHERE v.comment_id = id AND v.vote_type = 'LIKE')")
    private Integer dislikeCount;

    @Column(name = "is_anonymous", nullable = false)
    @Builder.Default
    private Boolean isAnonymous = true;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

}
