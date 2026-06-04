package com.codeMentra.backend.entity.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProblemTopicId {
    private Long problemId;
    private Long topicId;
}
