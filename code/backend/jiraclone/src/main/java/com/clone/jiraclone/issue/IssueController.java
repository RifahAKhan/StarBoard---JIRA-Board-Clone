package com.clone.jiraclone.issue;

import com.clone.jiraclone.issueactivity.IssueActivityRepository;
import com.clone.jiraclone.issueactivity.IssueActivityService;
import com.clone.jiraclone.utils.ActivityType;
import com.clone.jiraclone.utils.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private IssueActivityService issueActivityService;

    @Operation(summary = "Create a new issue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue has been successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
//    @PostMapping("/create-issue")
//    public IssueEntity createIssue(@RequestBody IssueEntity issue, Authentication authentication) {
//        // Extract the username from the Authentication object
//        String reporter = authentication.getName();  // This gives the username of the authenticated user
//
//        // Set the reporter field to the authenticated user's name
//        issue.setReporter(reporter);
//
//        // Create the issue using the service and return the saved issue
//        return issueService.createIssue(issue);
//    }
//    @PostMapping
//    public ResponseEntity<String> createIssue(@RequestBody IssueDTO issue) {
//        issueService.createIssue(issue);
////        issueActivityService.logActivity(ActivityType.ISSUE_CREATED, "Issue " + issue.getProjectName() + " created", issue.getReporter(), issue.getProjectId());
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body("Issue has been successfully created");
//    }
    @PostMapping
    public ResponseEntity<SuccessResponse> createIssue(@RequestBody IssueDTO issue) {
        issueService.createIssue(issue);
        // issueActivityService.logActivity(ActivityType.ISSUE_CREATED, "Issue " + issue.getProjectName() + " created", issue.getReporter(), issue.getProjectId());
        SuccessResponse response = new SuccessResponse("Issue created successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @Operation(summary = "Get an issue by project ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the issue", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = IssueEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Issue not found", content = @Content)
    })
    @GetMapping("/project/{projectId}")
    public ResponseEntity<IssueDTO> getProjectById(@PathVariable Long projectId) {
        return issueService.getIssueByProjectId(projectId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

//    @Operation(summary = "Update an issue by ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Issue has been successfully edited", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
//            @ApiResponse(responseCode = "404", description = "Issue not found", content = @Content)
//    })
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateIssue(@PathVariable Long id, @RequestBody IssueDTO updatedIssue) {
//        return issueService.updateIssue(id, updatedIssue)
//                .map(issue -> ResponseEntity.ok("Issue has been successfully edited"))
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//    }

    @Operation(summary = "Update an issue by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue has been successfully edited", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "Issue not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateIssue(@PathVariable Long id, @RequestBody IssueDTO updatedIssue) {
        return issueService.updateIssue(id, updatedIssue)
                .map(issue -> ResponseEntity.ok(new SuccessResponse("Issue has been successfully edited")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("Issue not found")));
    }

    @Operation(summary = "Get all issues")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all issues", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = IssueEntity.class))}),
            @ApiResponse(responseCode = "404", description = "No issues found", content = @Content)
    })
    @GetMapping("/all")
    public ResponseEntity<List<IssueDTO>> getAllIssues() {
        List<IssueDTO> issues = issueService.getAllProjects();
        if (issues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(issues);
    }

    @Operation(summary = "Get issues by assignee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved issues for the assignee",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = IssueEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No issues found for the given assignee",
                    content = @Content)
    })
    @GetMapping("/assignee/{assignee}")
    public ResponseEntity<List<IssueDTO>> getIssuesByAssignee(@PathVariable String assignee) {
        List<IssueDTO> issues = issueService.getIssuesByAssignee(assignee);
        if (issues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(issues);
        }
        return ResponseEntity.ok(issues);
    }
    @Operation(summary = "Move issue to active Sprint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue has been successfully moved to active sprint", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "Issue not found", content = @Content)
    })
    @PutMapping("/activate")
    public ResponseEntity<SuccessResponse> activateIssues(@RequestBody ActivateIssuesRequest request) {
        boolean success = issueService.activateIssues(request.getProjectIds());
        if (success) {
            return ResponseEntity.ok(new SuccessResponse("Issue has been successfully moved to Active Sprint"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("Issue not found"));
        }
    }

    @Operation(summary = "Delete active issues")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issues have been successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "No active issues found", content = @Content)
    })
    @DeleteMapping("/delete-active")
    public ResponseEntity<SuccessResponse> deleteActiveIssues(@RequestBody ActivateIssuesRequest request) {
        boolean success = issueService.deleteActiveIssues(request.getProjectIds());
        if (success) {
            return ResponseEntity.ok(new SuccessResponse("Issues have been successfully deleted"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("No active issues found"));
        }
    }
}
