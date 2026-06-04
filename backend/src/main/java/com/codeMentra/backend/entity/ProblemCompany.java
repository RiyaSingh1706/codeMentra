package com.codeMentra.backend.entity;

import com.codeMentra.backend.entity.embeddable.ProblemCompanyId;
import com.codeMentra.backend.enums.Popularity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "problem_companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemCompany {

    @EmbeddedId
    private ProblemCompanyId id;

    @ManyToOne
    @MapsId("problemId")
    @JoinColumn(name = "problem_Id")
    private Problem problem;


    @ManyToOne
    @MapsId("companyId")
    @JoinColumn(name = "company_Id")
    private Company company;

    @Enumerated(EnumType.STRING)
    private Popularity popularity;
}
