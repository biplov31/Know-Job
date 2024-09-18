package com.example.KnowJob.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NaturalId
    @Column(name = "work_email")
    private String workEmail;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "roles", nullable = false)
    private String roles;

    @ManyToMany(
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            },
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "user_company",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id")
    )
    private Set<Company> companies = new HashSet<>();

    // should be able to access all the posts made by a user
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    public void addCompany(Company company) {
        companies.add(company);
        company.getUsers().add(this);
    }

    public void removeCompany(Company company) {
        companies.remove(company);
        company.getUsers().remove(this);
    }

    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    public void removePost(Post post) {
        posts.remove(post);
        post.setUser(null);
    }

}
