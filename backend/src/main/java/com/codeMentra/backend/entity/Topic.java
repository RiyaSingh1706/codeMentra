package com.codeMentra.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;


    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Topic parentTopic;

    @OneToMany(mappedBy = "parentTopic")
    private List<Topic> subTopics;
}
