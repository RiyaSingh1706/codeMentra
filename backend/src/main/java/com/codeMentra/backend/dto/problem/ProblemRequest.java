package com.codeMentra.backend.dto.problem;

import com.codeMentra.backend.enums.Difficulty;
import com.codeMentra.backend.enums.Platform;
import com.codeMentra.backend.enums.Popularity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemRequest {
    @NotBlank
    private String title;

    @NotNull
    private Difficulty difficulty;

    @NotNull
    private Platform platform;

    @NotNull
    private Popularity popularity;

    @NotBlank
    private String problemUrl;

    private String pattern;
    private Integer avgSolveTime;
    private Set<Long> topicIds;
    private Set<Long> companyIds;

}
