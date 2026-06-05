package com.codeMentra.backend.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserTargetCompanyId {
    private Long userId;
    private Long companyId;
}
