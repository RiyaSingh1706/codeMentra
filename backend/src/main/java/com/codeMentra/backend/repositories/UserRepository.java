package com.codeMentra.backend.repositories;

import com.codeMentra.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
