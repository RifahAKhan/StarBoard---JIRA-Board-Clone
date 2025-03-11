package com.clone.jiraclone.issue;

import lombok.Data;

import java.util.List;

@Data
public class ActivateIssuesRequest {
    private List<Long> projectIds;
}
