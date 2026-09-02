package com.codeMentra.backend.entity;

import com.codeMentra.backend.entity.embeddable.ProblemTopicId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "problem_topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemTopic {

    @EmbeddedId
    private ProblemTopicId id;

    @ManyToOne
    @MapsId("problemId")
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne
    @MapsId("topicId")
    @JoinColumn(name = "topic_id")
    private Topic topic;
}
