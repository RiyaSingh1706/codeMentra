package com.codeMentra.backend.dto.activity;

import com.codeMentra.backend.enums.ProblemStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponseUpdate {
    @NotNull
    private Long problemId;

    @NotNull
    private ProblemStatus status;

    private Integer selfReportedTimeMins;
    private Integer hintsRequired;
    private Integer aiInteractions;
    private Boolean codeSubmittedToAI;
}
