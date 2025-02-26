package com.clone.jiraclone.subtask;

import com.clone.jiraclone.subtaskactivity.SubtaskActivityDTO;
import com.clone.jiraclone.subtaskcomment.SubtaskCommentDTO;
import com.clone.jiraclone.utils.Priority;
import com.clone.jiraclone.utils.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Data
public class SubtaskDTO {
    private Long id;
    private Long issueId;
    private String summary;
    private Priority priority;
    private Date dueDate;
    private String assignee;
    private String description;
    private String labels;
    private Integer storyPoints;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Status status;
    private String statusLabel;
    private String reporter;
    private List<SubtaskCommentDTO> comments;
    private List<SubtaskActivityDTO> subtaskActivities;
    private List<Object> all;

    public void setSubtaskActivities(List<SubtaskActivityDTO> subtaskActivities) {
        this.subtaskActivities = subtaskActivities;
        if (this.subtaskActivities != null) {
            this.subtaskActivities.sort(Comparator.comparing(SubtaskActivityDTO::getTimestamp).reversed());
        }

    }
}