package com.clone.jiraclone.subtask;

import com.clone.jiraclone.exception.IssueNotFoundException;
import com.clone.jiraclone.exception.SubtaskNotFoundException;
import com.clone.jiraclone.issue.IssueRepository;
import com.clone.jiraclone.issueactivity.IssueActivityService;
import com.clone.jiraclone.subtaskactivity.SubtaskActivityDTO;
import com.clone.jiraclone.subtaskactivity.SubtaskActivityService;
import com.clone.jiraclone.subtaskcomment.SubtaskCommentCommentRepository;
import com.clone.jiraclone.subtaskcomment.SubtaskCommentDTO;
import com.clone.jiraclone.subtaskcomment.SubtaskCommentService;
import com.clone.jiraclone.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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

    @Autowired
    private SubtaskActivityService subtaskActivityService;

    @Autowired
    private IssueActivityService issueActivityService;

    public SubtaskDTO createSubtask(SubtaskDTO subtaskDTO) {
        if(!issueRepository.existsByProjectId(subtaskDTO.getIssueId())) {
            throw new IssueNotFoundException("Issue with id " + subtaskDTO.getIssueId() + " not found");
        }
        SubtaskEntity subtask = convertToEntity(subtaskDTO);
        subtask.setCreatedBy("system");
        subtask.setCreatedDate(LocalDateTime.now());
        SubtaskEntity savedSubtask = subtaskRepository.save(subtask);
        subtaskActivityService.logActivity(ActivityType.SUBTASK_CREATED, "Subtask created", "system", savedSubtask.getId());
        issueActivityService.logActivity(ActivityType.SUBTASK_CREATED, "Subtask has been created under ID : " + subtaskDTO.getIssueId(),"system", savedSubtask.getIssueId());

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

            if (!subtask.getStatus().equals(updatedSubtaskDTO.getStatus()) && updatedSubtaskDTO.getStatus()!=Status.DONE){
                subtaskActivityService.logActivity(ActivityType.STATUS_TRANSITION, "Subtask status changed to " + updatedSubtaskDTO.getStatus(), updatedSubtask.getReporter(), subtask.getId());
            }
            if(!subtask.getAssignee().equals(updatedSubtaskDTO.getAssignee())) {
                subtaskActivityService.logActivity(ActivityType.FIELD_CHANGED, "Subtask assignee changed to " + updatedSubtaskDTO.getAssignee(), updatedSubtask.getReporter(), subtask.getId());
            }
            if(!subtask.getPriority().equals(updatedSubtaskDTO.getPriority())) {
                subtaskActivityService.logActivity(ActivityType.FIELD_CHANGED, "Subtask priority changed to " + updatedSubtaskDTO.getPriority(), updatedSubtask.getReporter(), subtask.getId());
            }
            if (Status.DONE.equals(updatedSubtaskDTO.getStatus()) && !subtask.getStatus().equals(updatedSubtaskDTO.getStatus())) {
                subtaskActivityService.logActivity(ActivityType.SUBTASK_RESOLVED, "Subtask resolved", updatedSubtask.getReporter(), subtask.getId());
            }
            SubtaskEntity savedSubtask = subtaskRepository.save(updatedSubtask);
            return convertSubtaskToDTO(savedSubtask);
        }).orElseThrow(() -> new SubtaskNotFoundException("Subtask with id " + id + " not found")));
    }

    private SubtaskEntity convertToEntity(SubtaskDTO subtaskDTO) {
        SubtaskEntity subtask = new SubtaskEntity();
        subtask.setId(subtaskDTO.getId());
        subtask.setIssueId(subtaskDTO.getIssueId());
        subtask.setSummary(subtaskDTO.getSummary());
        subtask.setPriority(subtaskDTO.getPriority());
        subtask.setDueDate(subtaskDTO.getDueDate());
        subtask.setAssignee(subtaskDTO.getAssignee());
        subtask.setDescription(subtaskDTO.getDescription());
        subtask.setLabels(IssueType.valueOf(subtaskDTO.getLabels()));
        subtask.setStoryPoints(subtaskDTO.getStoryPoints());
        subtask.setCreatedBy(subtaskDTO.getCreatedBy());
        subtask.setUpdatedBy(subtaskDTO.getUpdatedBy());
        subtask.setCreatedDate(subtaskDTO.getCreatedDate());
        subtask.setUpdatedDate(subtaskDTO.getUpdatedDate());
        subtask.setStatus(subtaskDTO.getStatus());
        subtask.setStatusLabel(StatusLabel.valueOf(subtaskDTO.getStatusLabel()));
        subtask.setReporter(subtaskDTO.getReporter());
        return subtask;
    }

    public SubtaskDTO convertSubtaskToDTO(SubtaskEntity subtask) {
        SubtaskDTO subtaskDTO = new SubtaskDTO();
        subtaskDTO.setId(subtask.getId());
        subtaskDTO.setIssueId(subtask.getIssueId());
        subtaskDTO.setSummary(subtask.getSummary());
        subtaskDTO.setPriority(subtask.getPriority());
        subtaskDTO.setDueDate(subtask.getDueDate());
        subtaskDTO.setAssignee(subtask.getAssignee());
        subtaskDTO.setDescription(subtask.getDescription());
        subtaskDTO.setLabels(String.valueOf(subtask.getLabels()));
        subtaskDTO.setStoryPoints(subtask.getStoryPoints());
        subtaskDTO.setCreatedBy(subtask.getCreatedBy());
        subtaskDTO.setUpdatedBy(subtask.getUpdatedBy());
        subtaskDTO.setCreatedDate(subtask.getCreatedDate());
        subtaskDTO.setUpdatedDate(subtask.getUpdatedDate());
        subtaskDTO.setStatus(subtask.getStatus());
        subtaskDTO.setStatusLabel(String.valueOf(subtask.getStatusLabel()));
        subtaskDTO.setReporter(subtask.getReporter());

        List<SubtaskCommentDTO> comments = subtaskCommentService.getCommentsBySubtaskId(subtask.getId());
        subtaskDTO.setComments(comments);

        List<SubtaskActivityDTO> subactivities = subtaskActivityService.getAllSubtaskActivity(subtask.getId());
        subactivities.sort(Comparator.comparing(SubtaskActivityDTO::getTimestamp).reversed());
        subtaskDTO.setSubtaskActivities(subactivities);

        List<Object> all= new ArrayList<>();
        all.addAll(comments);
        all.addAll(subactivities);

        subtaskDTO.setAll(all);
        return subtaskDTO;
    }

    public List<SubtaskDTO> getAllSubtasks() {
        return subtaskRepository.findAll().stream()
                .map(this::convertSubtaskToDTO)
                .sorted(Comparator.comparing(SubtaskDTO::getCreatedDate))
                .collect(Collectors.toList());
    }

    public Optional<SubtaskDTO> getSubtaskById(Long id) {
        return subtaskRepository.findById(id).map(this::convertSubtaskToDTO);
    }

}
