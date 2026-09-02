package com.codeMentra.backend.specification;

import com.codeMentra.backend.entity.Problem;
import com.codeMentra.backend.entity.ProblemCompany;
import com.codeMentra.backend.entity.ProblemTopic;
import com.codeMentra.backend.enums.Difficulty;
import com.codeMentra.backend.enums.Platform;
import com.codeMentra.backend.enums.Popularity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ProblemSpecification {

    public static Specification<Problem> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("isActive"));
    }

    public static Specification<Problem> hasDifficulty(Difficulty difficulty) {
        return (root, query, cb) ->
                difficulty == null ? null : cb.equal(root.get("difficulty"), difficulty);
    }

    public static Specification<Problem> hasPlatform(Platform platform) {
        return (root, query, cb) ->
                platform == null ? null : cb.equal(root.get("platform"), platform);
    }

    public static Specification<Problem> hasPopularity(Popularity popularity) {
        return (root, query, cb) ->
                popularity == null ? null : cb.equal(root.get("popularity"), popularity);
    }

    public static Specification<Problem> hasTopic(String topicName) {
        return (root, query, cb) -> {
            if (topicName == null || topicName.isBlank()) return null;
            query.distinct(true);
            Join<Problem, ProblemTopic> join = root.join("problemTopics");
            return cb.equal(cb.lower(join.get("topic").get("name")), topicName.toLowerCase());
        };
    }

    public static Specification<Problem> hasCompany(String companyName) {
        return (root, query, cb) -> {
            if (companyName == null || companyName.isBlank()) return null;
            query.distinct(true);
            Join<Problem, ProblemCompany> join = root.join("problemCompanies");
            return cb.equal(cb.lower(join.get("company").get("name")), companyName.toLowerCase());
        };
    }
}