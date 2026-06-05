package com.codeMentra.backend.repositories;

import com.codeMentra.backend.entity.ProblemTopic;
import com.codeMentra.backend.entity.embeddable.ProblemTopicId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTopicRepository extends JpaRepository<ProblemTopic, ProblemTopicId> {
}
