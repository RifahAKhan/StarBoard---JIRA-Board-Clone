package com.clone.jiraclone.issue;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity(name = "issue")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_id_seq")
    @SequenceGenerator(name = "issue_id_seq", sequenceName = "issue_id_seq", allocationSize = 1)
    private int id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueType issueType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private String labels;

    @Column(nullable = false)
    private String storyPoints;

    @Column(nullable = false)
    private int assignee;

    @Column(nullable = false)
    private String project;

}
