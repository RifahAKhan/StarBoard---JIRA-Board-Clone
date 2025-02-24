package com.clone.jiraclone.issue;


import com.clone.jiraclone.comment.CommentDTO;
import com.clone.jiraclone.subtask.SubtaskDTO;
import com.clone.jiraclone.utils.IssueType;
import com.clone.jiraclone.utils.Priority;
import com.clone.jiraclone.utils.Status;
import com.clone.jiraclone.utils.StatusLabel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class IssueDTO {
    private int id;
    private String description;
    private IssueType issueType;
    private Priority priority;
    private String summary;
    private String labels;
    private String storyPoints;
    private String assignee;
    private String projectName;
    private Long projectId;
    private String sprint;
    private String reporter;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Status status;
    private StatusLabel statusLabel;
    private List<SubtaskDTO> subtasks;
    private List<CommentDTO> comments;
}