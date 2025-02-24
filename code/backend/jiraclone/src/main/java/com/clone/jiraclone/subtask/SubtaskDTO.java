package com.clone.jiraclone.subtask;

import com.clone.jiraclone.subtaskcomment.SubtaskCommentDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class SubtaskDTO {
    private Long id;
    private Long issueId;
    private String summary;
    private String priority;
    private Date dueDate;
    private String assignee;
    private String description;
    private String labels;
    private Integer storyPoints;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String status;
    private String statusLabel;
    private List<SubtaskCommentDTO> comments;
}