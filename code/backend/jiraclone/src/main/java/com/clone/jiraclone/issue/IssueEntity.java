package com.clone.jiraclone.issue;

import com.clone.jiraclone.utils.IssueType;
import com.clone.jiraclone.utils.Priority;
import com.clone.jiraclone.utils.Status;
import com.clone.jiraclone.utils.StatusLabel;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private String assignee;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = false, unique = true)
    private Long projectId;

    @Column(nullable = false)
    private String sprint;

    @Column(nullable = false)
    private String reporter;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusLabel statusLabel;


}