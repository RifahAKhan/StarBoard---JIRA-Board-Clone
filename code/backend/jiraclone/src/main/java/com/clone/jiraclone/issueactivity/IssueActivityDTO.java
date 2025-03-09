package com.clone.jiraclone.issueactivity;

import com.clone.jiraclone.utils.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssueActivityDTO {
    private ActivityType type;
    private String description;
    private String userName;
    private LocalDateTime createdDate;
    private Long id;
    private Long issueId;

}
