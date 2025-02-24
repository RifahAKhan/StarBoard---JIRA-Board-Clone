package com.clone.jiraclone.subtask;

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
@RequestMapping("/api/subtasks")
public class SubtaskController {

    @Autowired
    private SubtaskService subtaskService;

    @Operation(summary = "Create a new subtask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subtask has been created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<String> createSubtask(@RequestBody SubtaskDTO issue) {
        subtaskService.createSubtask(issue);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Subtask has been created successfully");
    }

    @Operation(summary = "Update a subtask by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subtask has been successfully edited", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Subtask not found", content = @Content)
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateSubtask(@PathVariable Long id, @RequestBody SubtaskDTO updatedSubtask) {
        return subtaskService.updateSubtask(id, updatedSubtask)
                .map(issue -> ResponseEntity.ok("Subtask has been edited successfully"))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubtaskDTO>> getAllSubtasks() {
        List<SubtaskDTO> subtasks = subtaskService.getAllSubtasks();
        if(subtasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(subtasks);
    }

    @Operation(summary = "Get a subtask by subtask ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the subtask", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SubtaskEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Subtask not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SubtaskDTO> getSubtaskById(@PathVariable Long id) {
        return subtaskService.getSubtaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}