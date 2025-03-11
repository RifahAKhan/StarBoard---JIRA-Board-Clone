package com.clone.jiraclone.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SuccessResponse {
    private String message;

    public SuccessResponse(String message) {
        this.message = message;
    }
}
