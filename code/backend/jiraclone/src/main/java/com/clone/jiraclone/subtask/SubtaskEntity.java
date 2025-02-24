package com.clone.jiraclone.subtask;

import com.clone.jiraclone.utils.IssueType;
import com.clone.jiraclone.utils.Priority;
import com.clone.jiraclone.utils.Status;
import com.clone.jiraclone.utils.StatusLabel;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "subtask")
@Data
public class SubtaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "issue_id", nullable = false)
    private Long issueId;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Column(name = "assignee", nullable = false)
    private String assignee;

    @Column(name = "description", nullable = false)
    private String description;

//    @Column(name = "labels", nullable = false)
//    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueType labels;

    @Column(name = "story_points", nullable = false)
    private int storyPoints;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusLabel statusLabel;
}