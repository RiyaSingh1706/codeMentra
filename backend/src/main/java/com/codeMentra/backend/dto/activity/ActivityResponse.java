package com.codeMentra.backend.dto.activity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityResponse {
    private Long id;
    private String problemTitle;
    private String status;
    private Integer hintsRequired;
    private Integer aiInteractions;
    private Boolean codeSubmittedToAI;
    private Integer selfReportedTimeMins;
    private LocalDateTime markedSolvedAt;
    private LocalDateTime lastActivityAt;
}
