package com.codeMentra.backend.repositories;

import com.codeMentra.backend.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
