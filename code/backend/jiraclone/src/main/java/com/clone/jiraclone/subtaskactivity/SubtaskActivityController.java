package com.clone.jiraclone.subtaskactivity;

import com.clone.jiraclone.issueactivity.IssueActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subtaskActivity")
public class SubtaskActivityController {

    @Autowired
    private SubtaskActivityService SubtaskActivityService;

}
