package com.codeMentra.backend.dto.problem;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemResponse {
    private Long id;
    private String title;
    private String difficulty;
    private String platform;
    private String popularity;
    private String problemUrl;
    private String pattern;
    private Integer avgSolveTime;
    private Boolean isActive;
    private Set<String> topics;
    private Set<String> companies;
}
