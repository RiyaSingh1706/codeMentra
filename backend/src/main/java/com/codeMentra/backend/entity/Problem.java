package com.codeMentra.backend.entity;

import com.codeMentra.backend.enums.Difficulty;
import com.codeMentra.backend.enums.Platform;
import com.codeMentra.backend.enums.Popularity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "problems")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Enumerated(EnumType.STRING)
    private Popularity popularity;

    private String problemUrl;

    private String pattern;

    private Boolean isActive = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private Set<ProblemTopic> problemTopics = new HashSet<>();


    @Builder.Default
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<ProblemCompany> problemCompanies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "problem")
    private List<UserProblemActivity> userActivities = new ArrayList<>();
}
