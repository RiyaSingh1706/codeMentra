package com.codeMentra.backend.dto.topic;

import jakarta.persistence.NamedStoredProcedureQueries;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SecondaryRow;

@Getter
@SecondaryRow
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicResponse {
    private Long id;
    private String name;
    private String description;
    private String parentTopicName;
}
