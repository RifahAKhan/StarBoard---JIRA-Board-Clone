package com.clone.jiraclone.subtaskcomment;

import com.clone.jiraclone.exception.SubtaskCommentNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subtask-comment")
public class SubtaskCommentController {

    @Autowired
    private SubtaskCommentService commentService;

    @Operation(summary = "Create new Comment-subtask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subtask Comment has been created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping("/create")
    public SubtaskCommentDTO createComment(@RequestBody SubtaskCommentDTO commentDTO,@RequestParam Long subtaskId) {
        return commentService.createComment(commentDTO,subtaskId);
    }

    @Operation(summary = "Edit Comment-subtask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subtask Comment has been edited successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PutMapping("/{id}")
    public SubtaskCommentDTO updateComment(@PathVariable Long id, @RequestBody SubtaskCommentDTO commentDTO) {
        return commentService.updateComment(id, commentDTO).orElseThrow(() -> new SubtaskCommentNotFoundException("Comment not found"));
    }

    @Operation(summary = "Get all subtask comment by subtask id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all comments of subtask", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SubtaskCommentEntity.class))}),
            @ApiResponse(responseCode = "404", description = "No Comments found", content = @Content)
    })
    @GetMapping("/subtask/{subtaskId}")
    public List<SubtaskCommentDTO> getCommentsBySubtaskId(@PathVariable Long subtaskId) {
        return commentService.getCommentsBySubtaskId(subtaskId);
    }
}
