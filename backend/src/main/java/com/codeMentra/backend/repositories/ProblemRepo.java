package com.codeMentra.backend.repositories;

import com.codeMentra.backend.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProblemRepo extends JpaRepository<Problem, Long>, JpaSpecificationExecutor<Problem> {
}
