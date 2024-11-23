package com.example.KnowJob.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "department")
    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private Department department = Department.INTERNSHIP;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "like_count", nullable = true)
    @Formula("(SELECT COUNT(v.id) FROM votes v WHERE v.review_id = id AND v.vote_type = 'LIKE')")
    private Integer likeCount;

    @Column(name = "dislike_count", nullable = true)
    @Formula("(SELECT COUNT(v.id) FROM votes v WHERE v.review_id = id AND v.vote_type = 'DISLIKE')")
    private Integer dislikeCount;

    @Column(name = "is_anonymous", nullable = false)
    @Builder.Default
    private Boolean isAnonymous = true;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    // Lombok's ToString and EqualsAndHashCode cause an infinite recursive loop leading to a stackoverflow error
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Comment> comments = new HashSet<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setReview(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setReview(null);
    }


}
