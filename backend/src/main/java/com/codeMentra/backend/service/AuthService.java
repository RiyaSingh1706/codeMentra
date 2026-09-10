package com.codeMentra.backend.service;

import com.codeMentra.backend.dto.auth.*;
import com.codeMentra.backend.entity.RefreshToken;
import com.codeMentra.backend.entity.User;
import com.codeMentra.backend.enums.Role;
import com.codeMentra.backend.repositories.RefreshTokenRepository;
import com.codeMentra.backend.repositories.UserRepository;
import com.codeMentra.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

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
                .platform(request.getPlatform())
                .build();

        userRepository.save(user);

        return  buildAuthResponse(user);
    }

    @Transactional
    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByUsernameOrEmail(
                        request.getUsernameOrEmail(),request.getUsernameOrEmail())
                .orElseThrow(() -> new org.springframework.security.authentication.BadCredentialsException("Invalid credentials"));

        if(!passwordEncoder.matches(request.getPassword(),user.getPasswordHash())){
            throw new org.springframework.security.authentication.BadCredentialsException("Invalid credentials");
        }

        return buildAuthResponse(user);
    }

    @Transactional
    public AuthResponse refresh(RefreshRequest request){
        RefreshToken oldToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if(Boolean.TRUE.equals(oldToken.getIsRevoked()) || oldToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Refresh token expired or revoked");
        }

        User user = oldToken.getUser();

        refreshTokenRepository.delete(oldToken);

        return buildAuthResponse(user);
    }

    @Transactional
    public void logout(LogoutRequest request){
        RefreshToken token = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
        refreshTokenRepository.delete(token);
    }

    private AuthResponse buildAuthResponse(User user){
        String accessToken = jwtUtil.generateToken(user.getUsername());

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiresAt(LocalDateTime.now().plusSeconds(refreshExpirationMs / 1000))
                .isRevoked(false)
                .build();
        refreshTokenRepository.save(refreshToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}