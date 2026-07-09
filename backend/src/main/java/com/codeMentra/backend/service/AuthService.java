package com.codeMentra.backend.service;

import com.codeMentra.backend.dto.auth.AuthResponse;
import com.codeMentra.backend.dto.auth.LoginRequest;
import com.codeMentra.backend.dto.auth.SignUpRequest;
import com.codeMentra.backend.entity.User;
import com.codeMentra.backend.enums.Role;
import com.codeMentra.backend.repositories.UserRepository;
import com.codeMentra.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse signup(SignUpRequest request){
        //checking if email or username already exists
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already registered");
        }
        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username already taken");
        }

        //build user entity
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .preferredLanguage(request.getPreferredLanguage())
                .platform(request.getPreferedPlatform())
                .role(Role.USER)
                .build();

        //save to db
        userRepository.save(user);

        //generate token
        String token = jwtUtil.generateToken(user.getUsername());

        //return response
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public AuthResponse login(LoginRequest request){

        //authenticate- Spring Security checks credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        //get username
        String username = authentication.getName();

        //fetch user role
        User user = userRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //generate token
        String token = jwtUtil.generateToken(username);

        //return response
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
