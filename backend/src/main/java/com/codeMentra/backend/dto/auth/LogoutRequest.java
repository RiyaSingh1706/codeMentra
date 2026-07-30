package com.codeMentra.backend.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LogoutRequest {
    @NotBlank
    private String refreshToken;
}