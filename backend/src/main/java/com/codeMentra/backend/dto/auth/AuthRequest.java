package com.codeMentra.backend.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequest {
    private String token;
    private String username;
    private String email;
    private String role;
}
