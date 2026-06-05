package com.codeMentra.backend.repositories;

import com.codeMentra.backend.entity.UserTopicScores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTopicScoreRepository extends JpaRepository<UserTopicScores, Long> {
}
