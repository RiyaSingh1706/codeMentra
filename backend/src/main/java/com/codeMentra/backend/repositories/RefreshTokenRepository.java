package com.codeMentra.backend.repositories;

import com.codeMentra.backend.entity.RefreshToken;
import com.codeMentra.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}
