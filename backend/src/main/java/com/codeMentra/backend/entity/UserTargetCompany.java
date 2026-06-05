package com.codeMentra.backend.entity;

import com.codeMentra.backend.entity.embeddable.UserTargetCompanyId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_target_companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTargetCompany {

    @EmbeddedId
    private UserTargetCompanyId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
    private Company company;
}
