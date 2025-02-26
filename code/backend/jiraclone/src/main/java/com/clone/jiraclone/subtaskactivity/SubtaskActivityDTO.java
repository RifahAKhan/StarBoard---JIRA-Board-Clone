package com.clone.jiraclone.subtaskactivity;

import com.clone.jiraclone.utils.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubtaskActivityDTO {
    private ActivityType type;
    private String description;
    private String userName;
    private LocalDateTime timestamp;
    private Long id;
    private Long issueId;
}
