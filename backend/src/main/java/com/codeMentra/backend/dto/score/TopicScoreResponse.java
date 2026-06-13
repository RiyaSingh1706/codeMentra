package com.codeMentra.backend.dto.score;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicScoreResponse {
    private Long topicId;
    private String topicName;
    private BigDecimal score;
    private Integer problemsAttempted;
    private Integer problemSolved;
    private String skillLevel; //derived: Beginner/Intermediate/Advanced
}
