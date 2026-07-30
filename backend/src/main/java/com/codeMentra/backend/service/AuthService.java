package com.codeMentra.backend.service;

import com.codeMentra.backend.dto.auth.AuthResponse;
import com.codeMentra.backend.dto.auth.SignUpRequest;
import com.codeMentra.backend.entity.User;
import com.codeMentra.backend.enums.Role;
import com.codeMentra.backend.repositories.RefreshTokenRepository;
import com.codeMentra.backend.repositories.UserRepository;
import com.codeMentra.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @org.springframework.beans.factory.annotation.Value("${jwt.refresh.expiration}")
    private long refreshExpirationMs;

    public AuthResponse register(SignUpRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new IllegalArgumentException("Username already taken");
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .preferredLanguage(request.getPreferredLanguage())
                .platform(request.getPreferedPlatform())
                .build();
    }
}