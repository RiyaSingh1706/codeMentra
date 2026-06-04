package com.codeMentra.backend.repositories;

import com.codeMentra.backend.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepo extends JpaRepository<Problem, Long> {
}
