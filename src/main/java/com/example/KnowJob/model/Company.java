package com.example.KnowJob.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @NaturalId
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    // average rating and total review count

    @ManyToMany(mappedBy = "companies", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    public void addReview(Review review) {
        reviews.add(review);
        review.setCompany(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setCompany(null);
    }

}
