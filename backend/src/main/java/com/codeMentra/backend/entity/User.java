package com.codeMentra.backend.entity;


import com.codeMentra.backend.enums.Platform;
import com.codeMentra.backend.enums.PreferredLanguage;
import com.codeMentra.backend.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private PreferredLanguage preferredLanguage;

    @Enumerated(EnumType.STRING)
    private Platform platform;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "INTEGER DEFAULT 60")
    private Integer dailyGoalMinutes = 60;

    @OneToMany(mappedBy = "user")
    private List<UserProblemActivity> activities;

    @OneToMany(mappedBy = "user")
    private List<UserTopicScores> topicScores;

    @OneToMany(mappedBy = "user")
    private List<UserTargetCompany> targetCompanies;
}
