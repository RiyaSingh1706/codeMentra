package com.codeMentra.backend.config;

import com.codeMentra.backend.entity.*;
import com.codeMentra.backend.entity.embeddable.ProblemCompanyId;
import com.codeMentra.backend.entity.embeddable.ProblemTopicId;
import com.codeMentra.backend.enums.Difficulty;
import com.codeMentra.backend.enums.Platform;
import com.codeMentra.backend.enums.Popularity;
import com.codeMentra.backend.repositories.*;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner{

    private final ProblemRepo problemRepo;
    private final TopicRepository topicRepository;
    private final CompanyRepository companyRepository;
    private final ProblemTopicRepository problemTopicRepository;
    private final ProblemCompanyRepository problemCompanyRepository;

    @Override
    public void run(String... args) throws Exception{
        if(problemRepo.count()>0){
            log.info("Problems table already populated - skipping seeding.");
            return;
        }

        seedTopics();
        seedCompanies();
        seedProblems();

        log.info("Seeding complete.");
    }

    private void seedTopics() throws Exception{
        try(CSVReader reader = new CSVReader(new InputStreamReader(
                new ClassPathResource("data/topics.csv").getInputStream(), StandardCharsets.UTF_8
        ))){
            List<String[]> rows = reader.readAll();
            for(int i = 1;i<rows.size();i++){
                String name = rows.get(i)[0].trim();
                if(name.isEmpty()) continue;
                topicRepository.findByName(name).orElseGet(() ->
                        topicRepository.save(Topic.builder().name(name).build()));
            }
        }
        log.info("Seeded {} topics.", topicRepository.count());
    }

    private void seedCompanies() throws Exception{
        try(CSVReader reader = new CSVReader(new InputStreamReader(
                new ClassPathResource("data/companies.csv").getInputStream(), StandardCharsets.UTF_8
        ))){
            List<String[]> rows = reader.readAll();
            for(int i = 1;i<rows.size();i++){
                String name = rows.get(i)[0].trim();
                if(name.isEmpty()) continue;
                companyRepository.findByName(name).orElseGet(() ->
                        companyRepository.save(Company.builder().name(name).build()));
            }

        }
        log.info("Seeded {} companies.",companyRepository.count());
    }
    private void seedProblems() throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(
                new ClassPathResource("data/problems.csv").getInputStream(), StandardCharsets.UTF_8))) {

            List<String[]> rows = reader.readAll();
            Map<String, Problem> problemCache = new HashMap<>(); // keyed by problemUrl
            int problemsCreated = 0;
            int topicLinksCreated = 0;

            for (int i = 1; i < rows.size(); i++) { // skip header
                String[] row = rows.get(i);
                if (row.length < 9) {
                    log.warn("Skipping malformed row {}: {}", i, String.join(",", row));
                    continue;
                }

                // columns: 0=#, 1=Title, 2=Difficulty, 3=Platform, 4=URL, 5=Topic, 6=Pattern, 7=Popularity, 8=Companies
                String url = row[4].trim();

                Problem problem = problemCache.get(url);
                if (problem == null) {
                    problem = Problem.builder()
                            .title(row[1].trim())
                            .difficulty(Difficulty.valueOf(row[2].trim().toUpperCase()))
                            .platform(Platform.valueOf(row[3].trim().toUpperCase()))
                            .problemUrl(url)
                            .pattern(row[6].trim())
                            .popularity(Popularity.valueOf(row[7].trim().toUpperCase()))
                            .isActive(true)
                            .build();
                    problem = problemRepo.save(problem);
                    problemCache.put(url, problem);
                    linkCompanies(problem, row[8]);
                    problemsCreated++;
                }

                linkTopic(problem, row[5].trim());
                topicLinksCreated++;
            }
            log.info("Seeded {} unique problems, {} topic links.", problemsCreated, topicLinksCreated);
        }
    }

    private void linkTopic(Problem problem, String topicName) {
        if (topicName.isEmpty()) return;
        Topic topic = topicRepository.findByName(topicName)
                .orElseThrow(() -> new IllegalStateException("Unknown topic: " + topicName));

        ProblemTopic link = ProblemTopic.builder()
                .id(new ProblemTopicId(problem.getId(), topic.getId()))
                .problem(problem)
                .topic(topic)
                .build();
        problemTopicRepository.save(link);
    }

    private void linkCompanies(Problem problem, String companiesCell) {
        if (companiesCell == null || companiesCell.isBlank()) return;
        for (String companyName : companiesCell.split(",")) {
            String name = companyName.trim();
            if (name.isEmpty()) continue;
            Company company = companyRepository.findByName(name)
                    .orElseThrow(() -> new IllegalStateException("Unknown company: " + name));

            ProblemCompany link = ProblemCompany.builder()
                    .id(new ProblemCompanyId(problem.getId(), company.getCompanyId()))
                    .problem(problem)
                    .company(company)
                    .build();
            problemCompanyRepository.save(link);
        }
    }

}
