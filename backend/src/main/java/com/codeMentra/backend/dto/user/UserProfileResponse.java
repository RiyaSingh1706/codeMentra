package com.codeMentra.backend.dto.user;

import com.codeMentra.backend.entity.UserTargetCompany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {
    private Long id;
    private String username;
    private String preferredPlatform;
    private String preferredLanguage;
    private String email;
    private String role;
    private Integer dailyGoalMinute;
    private LocalDateTime createdAt;
    private List<UserTargetCompany> targetCompanies;
}
