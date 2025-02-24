package com.clone.jiraclone.subtask;

import com.clone.jiraclone.exception.IssueNotFoundException;
import com.clone.jiraclone.exception.SubtaskNotFoundException;
import com.clone.jiraclone.issue.IssueRepository;
import com.clone.jiraclone.subtaskcomment.SubtaskCommentCommentRepository;
import com.clone.jiraclone.subtaskcomment.SubtaskCommentDTO;
import com.clone.jiraclone.subtaskcomment.SubtaskCommentService;
import com.clone.jiraclone.utils.IssueType;
import com.clone.jiraclone.utils.Priority;
import com.clone.jiraclone.utils.Status;
import com.clone.jiraclone.utils.StatusLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubtaskService {

    @Autowired
    private SubtaskRepository subtaskRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private SubtaskCommentCommentRepository subtaskCommentRepository;

    @Autowired
    private SubtaskCommentService subtaskCommentService;

    public SubtaskDTO createSubtask(SubtaskDTO subtaskDTO) {
        if(!issueRepository.existsById(subtaskDTO.getIssueId())) {
            throw new IssueNotFoundException("Issue with id " + subtaskDTO.getIssueId() + " not found");
        }
        SubtaskEntity subtask = convertToEntity(subtaskDTO);
        subtask.setCreatedBy("system");
        subtask.setCreatedDate(LocalDateTime.now());
        SubtaskEntity savedSubtask = subtaskRepository.save(subtask);
        return convertSubtaskToDTO(savedSubtask);
    }

    public Optional<SubtaskDTO> updateSubtask(Long id, SubtaskDTO updatedSubtaskDTO) {
        return Optional.ofNullable(subtaskRepository.findById(id).map(subtask -> {
            SubtaskEntity updatedSubtask = convertToEntity(updatedSubtaskDTO);
            updatedSubtask.setId(subtask.getId());
            updatedSubtask.setCreatedBy(subtask.getCreatedBy());
            updatedSubtask.setCreatedDate(subtask.getCreatedDate());
            updatedSubtask.setUpdatedBy("system");
            updatedSubtask.setUpdatedDate(LocalDateTime.now());
            SubtaskEntity savedSubtask = subtaskRepository.save(updatedSubtask);
            return convertSubtaskToDTO(savedSubtask);
        }).orElseThrow(() -> new SubtaskNotFoundException("Subtask with id " + id + " not found")));
    }

    private SubtaskEntity convertToEntity(SubtaskDTO subtaskDTO) {
        SubtaskEntity subtask = new SubtaskEntity();
        subtask.setId(subtaskDTO.getId());
        subtask.setIssueId(subtaskDTO.getIssueId());
        subtask.setSummary(subtaskDTO.getSummary());
        subtask.setPriority(Priority.valueOf(subtaskDTO.getPriority()));
        subtask.setDueDate(subtaskDTO.getDueDate());
        subtask.setAssignee(subtaskDTO.getAssignee());
        subtask.setDescription(subtaskDTO.getDescription());
        subtask.setLabels(IssueType.valueOf(subtaskDTO.getLabels()));
        subtask.setStoryPoints(subtaskDTO.getStoryPoints());
        subtask.setCreatedBy(subtaskDTO.getCreatedBy());
        subtask.setUpdatedBy(subtaskDTO.getUpdatedBy());
        subtask.setCreatedDate(subtaskDTO.getCreatedDate());
        subtask.setUpdatedDate(subtaskDTO.getUpdatedDate());
        subtask.setStatus(Status.valueOf(subtaskDTO.getStatus()));
        subtask.setStatusLabel(StatusLabel.valueOf(subtaskDTO.getStatusLabel()));
        return subtask;
    }

    public SubtaskDTO convertSubtaskToDTO(SubtaskEntity subtask) {
        SubtaskDTO subtaskDTO = new SubtaskDTO();
        subtaskDTO.setId(subtask.getId());
        subtaskDTO.setIssueId(subtask.getIssueId());
        subtaskDTO.setSummary(subtask.getSummary());
        subtaskDTO.setPriority(String.valueOf(subtask.getPriority()));
        subtaskDTO.setDueDate(subtask.getDueDate());
        subtaskDTO.setAssignee(subtask.getAssignee());
        subtaskDTO.setDescription(subtask.getDescription());
        subtaskDTO.setLabels(String.valueOf(subtask.getLabels()));
        subtaskDTO.setStoryPoints(subtask.getStoryPoints());
        subtaskDTO.setCreatedBy(subtask.getCreatedBy());
        subtaskDTO.setUpdatedBy(subtask.getUpdatedBy());
        subtaskDTO.setCreatedDate(subtask.getCreatedDate());
        subtaskDTO.setUpdatedDate(subtask.getUpdatedDate());
        subtaskDTO.setStatus(String.valueOf(subtask.getStatus()));
        subtaskDTO.setStatusLabel(String.valueOf(subtask.getStatusLabel()));
        List<SubtaskCommentDTO> comments = subtaskCommentService.getCommentsBySubtaskId(subtask.getId());
        subtaskDTO.setComments(comments);
        return subtaskDTO;
    }

    public List<SubtaskDTO> getAllSubtasks() {
        return subtaskRepository.findAll().stream()
                .map(this::convertSubtaskToDTO)
                .collect(Collectors.toList());
    }

    public Optional<SubtaskDTO> getSubtaskById(Long id) {
        return subtaskRepository.findById(id).map(this::convertSubtaskToDTO);
    }

}
