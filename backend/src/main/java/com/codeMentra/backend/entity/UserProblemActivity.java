package com.codeMentra.backend.entity;


import com.codeMentra.backend.enums.ProblemStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_problem_activity",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "problem_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProblemActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProblemStatus status;

    private Integer hintsRequested;

    private Integer aiInteractions;

    private Boolean codeSubmittedToAI = false;

    private Integer selfReportedTimeMin;

    private LocalDateTime platformSessionStart;

    private LocalDateTime markedSolvedAt;

    @UpdateTimestamp
    private LocalDateTime lastActivityAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;
}
