package com.codeMentra.backend.repositories;

import com.codeMentra.backend.entity.AiConversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiConversationRepository extends JpaRepository<AiConversation, Long> {
}
