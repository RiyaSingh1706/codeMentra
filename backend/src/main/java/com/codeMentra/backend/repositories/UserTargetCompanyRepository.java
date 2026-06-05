package com.codeMentra.backend.repositories;

import com.codeMentra.backend.entity.UserTargetCompany;
import com.codeMentra.backend.entity.embeddable.UserTargetCompanyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTargetCompanyRepository extends JpaRepository<UserTargetCompany, UserTargetCompanyId> {
}
