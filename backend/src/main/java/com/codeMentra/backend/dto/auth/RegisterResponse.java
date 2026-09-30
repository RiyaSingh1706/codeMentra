package com.codeMentra.backend.dto.auth;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private String message;
    private String email;
}
