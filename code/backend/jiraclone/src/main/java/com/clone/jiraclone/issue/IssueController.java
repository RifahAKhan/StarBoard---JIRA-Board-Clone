package com.clone.jiraclone.issue;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Operation(summary = "Create a new issue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue has been successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping
    public ResponseEntity<String> createIssue(@RequestBody IssueEntity issue) {
        issueService.createIssue(issue);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Issue has been successfully created");
    }


    @Operation(summary = "Get an issue by project ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the issue", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = IssueEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Issue not found", content = @Content)
    })
    @GetMapping("/project/{id}")
    public ResponseEntity<IssueEntity> getProjectById(@PathVariable Long id) {
        return issueService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }



    @Operation(summary = "Update an issue by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue has been successfully edited", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Issue not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateIssue(@PathVariable Long id, @RequestBody IssueEntity updatedIssue) {
        return issueService.updateIssue(id, updatedIssue)
                .map(issue -> ResponseEntity.ok("Issue has been successfully edited"))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Get all issues")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all issues", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = IssueEntity.class))}),
            @ApiResponse(responseCode = "404", description = "No issues found", content = @Content)
    })
    @GetMapping("/all")
    public ResponseEntity<List<IssueEntity>> getAllIssues() {
        List<IssueEntity> issues = issueService.getAllProjects();
        if (issues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(issues);
    }




}
