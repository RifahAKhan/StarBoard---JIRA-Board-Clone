package com.clone.jiraclone.issueactivity;

import com.clone.jiraclone.utils.ActivityType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "issue_activity")
public class IssueActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_activity_id_seq")
    @SequenceGenerator(name = "issue_activity_id_seq", sequenceName = "issue_activity_id_seq", allocationSize = 1)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType type;

    @Column(name="description", nullable = false)
    private String description;
    @Column(name="user_name", nullable = false)
    private String userName;
    @Column(name="timestamp", nullable = false)
    private LocalDateTime timestamp;
    @Column(name = "issue_id", nullable = false)
    private Long issueId;
}
