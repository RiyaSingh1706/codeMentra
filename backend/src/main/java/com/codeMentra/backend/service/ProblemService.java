package com.codeMentra.backend.service;

import com.codeMentra.backend.dto.problem.ProblemResponse;
import com.codeMentra.backend.entity.Problem;
import com.codeMentra.backend.enums.Difficulty;
import com.codeMentra.backend.enums.Platform;
import com.codeMentra.backend.enums.Popularity;
import com.codeMentra.backend.repositories.ProblemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.codeMentra.backend.specification.ProblemSpecification.*;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepo problemRepo;

    public Page<ProblemResponse> getProblems(
            Difficulty difficulty, Platform platform, Popularity popularity,
            String topic, String company, Pageable pageable) {

        Specification<Problem> spec = Specification
                .where(isActive())
                .and(hasDifficulty(difficulty))
                .and(hasPlatform(platform))
                .and(hasPopularity(popularity))
                .and(hasTopic(topic))
                .and(hasCompany(company));

        return problemRepo.findAll(spec, pageable).map(this::toResponse);
    }

    public ProblemResponse getProblemById(Long id) {
        Problem problem = problemRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Problem not found: " + id));
        return toResponse(problem);
    }

    private ProblemResponse toResponse(Problem p) {
        return ProblemResponse.builder()
                .id(p.getId())
                .title(p.getTitle())
                .difficulty(p.getDifficulty().name())
                .platform(p.getPlatform().name())
                .popularity(p.getPopularity().name())
                .problemUrl(p.getProblemUrl())
                .pattern(p.getPattern())
                .isActive(p.getIsActive())
                .avgSolveTime(null) // TODO: compute from UserProblemActivity once that feature exists
                .topics(p.getProblemTopics().stream()
                        .map(pt -> pt.getTopic().getName())
                        .collect(Collectors.toSet()))
                .companies(p.getProblemCompanies().stream()
                        .map(pc -> pc.getCompany().getName())
                        .collect(Collectors.toSet()))
                .build();
    }
}