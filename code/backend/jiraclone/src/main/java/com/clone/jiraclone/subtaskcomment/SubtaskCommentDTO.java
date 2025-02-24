package com.clone.jiraclone.subtaskcomment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubtaskCommentDTO {
    private Long id;
    private Long subtaskId;
    private String content;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
}
