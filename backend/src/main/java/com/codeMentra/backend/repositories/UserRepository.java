package com.codeMentra.backend.repositories;

import com.codeMentra.backend.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);

    boolean existsByEmail(@NotBlank @Email String email);

    boolean existsByUsername(@NotBlank String username);
}
