package com.codeMentra.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(
        name = "user_topic_scores",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "topic_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTopicScores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer problemsAttempted;

    private Integer problemsSolved;

    private LocalDateTime lastAttempted;

    private BigDecimal score;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}