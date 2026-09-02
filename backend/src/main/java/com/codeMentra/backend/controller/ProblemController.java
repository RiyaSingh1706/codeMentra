package com.codeMentra.backend.controller;

import com.codeMentra.backend.dto.problem.ProblemResponse;
import com.codeMentra.backend.enums.Difficulty;
import com.codeMentra.backend.enums.Platform;
import com.codeMentra.backend.enums.Popularity;
import com.codeMentra.backend.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping
    public ResponseEntity<Page<ProblemResponse>> getProblems(
            @RequestParam(required = false) Difficulty difficulty,
            @RequestParam(required = false) Platform platform,
            @RequestParam(required = false) Popularity popularity,
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) String company,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {

        return ResponseEntity.ok(
                problemService.getProblems(difficulty, platform, popularity, topic, company, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemResponse> getProblem(@PathVariable Long id) {
        return ResponseEntity.ok(problemService.getProblemById(id));
    }
}