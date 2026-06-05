package com.codeMentra.backend.repositories;

import com.codeMentra.backend.entity.ProblemCompany;
import com.codeMentra.backend.entity.embeddable.ProblemCompanyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemCompanyRepository extends JpaRepository<ProblemCompany, ProblemCompanyId> {
}
