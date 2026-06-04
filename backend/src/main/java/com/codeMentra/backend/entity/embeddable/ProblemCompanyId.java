package com.codeMentra.backend.entity.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.*;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemCompanyId {
    private Long problemId;
    private Long companyId;
}
