package com.codeMentra.backend.service;

import com.codeMentra.backend.dto.auth.*;
import com.codeMentra.backend.entity.RefreshToken;
import com.codeMentra.backend.entity.User;
import com.codeMentra.backend.enums.Role;
import com.codeMentra.backend.repositories.RefreshTokenRepository;
import com.codeMentra.backend.repositories.UserRepository;
import com.codeMentra.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    private final EmailService emailService;

    @Transactional
    public RegisterResponse register(SignUpRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        String verificationToken = UUID.randomUUID().toString();

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .preferredLanguage(request.getPreferredLanguage())
                .platform(request.getPlatform())
                .isEmailVerified(false)
                .verificationToken(verificationToken)
                .build();

        userRepository.save(user);
        emailService.sendVerificationEmail(user.getEmail(), user.getUsername(), verificationToken);

        return RegisterResponse.builder()
                .message("Account created. Please check your email to verify your account before logging in.")
                .email(user.getEmail())
                .build();
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsernameOrEmail(
                        request.getUsernameOrEmail(), request.getUsernameOrEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        if (!Boolean.TRUE.equals(user.getIsEmailVerified())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Email not verified. Please check your inbox for the verification link.");
        }

        return buildAuthResponse(user);
    }

    @Transactional
    public VerifyResponse verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired verification link"));

        user.setIsEmailVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);

        return VerifyResponse.builder()
                .message("Email verified successfully. You can now log in.")
                .build();
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