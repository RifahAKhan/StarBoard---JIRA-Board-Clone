package com.clone.jiraclone.issue;

import com.clone.jiraclone.comment.CommentDTO;
import com.clone.jiraclone.comment.CommentService;
import com.clone.jiraclone.exception.ProjectIdAndNameNotEditableException;
import com.clone.jiraclone.issueactivity.IssueActivityDTO;
import com.clone.jiraclone.issueactivity.IssueActivityService;
import com.clone.jiraclone.subtask.SubtaskDTO;
import com.clone.jiraclone.subtask.SubtaskRepository;
import com.clone.jiraclone.subtask.SubtaskService;
import com.clone.jiraclone.utils.ActivityType;
import com.clone.jiraclone.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.clone.jiraclone.exception.ProjectAlreadyExistsException;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private SubtaskRepository subtaskRepository;

    @Autowired
    private SubtaskService subtaskService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private IssueActivityService issueActivityService;

    public IssueDTO createIssue(IssueDTO issueDTO) {
        Optional<IssueEntity> existingIssue = issueRepository.findByProjectId(issueDTO.getProjectId());
        if (existingIssue.isPresent()) {
            throw new ProjectAlreadyExistsException("Project with the same id already exists");
        }
        IssueEntity issue = convertToEntity(issueDTO);
        IssueEntity savedIssue = issueRepository.save(issue);
        return convertToDTO(savedIssue);
    }

    public Optional<IssueDTO> getIssueByProjectId(Long projectId) {
        return issueRepository.findByProjectId(projectId).map(this::convertToDTOWithSubtasksAndComments);
    }

public Optional<IssueDTO> updateIssue(Long id, IssueDTO updatedIssueDTO) {
    return issueRepository.findById(id).map(issue -> {
        if (!issue.getProjectId().equals(updatedIssueDTO.getProjectId()) && !issue.getProjectName().equals(updatedIssueDTO.getProjectName())) {
            throw new ProjectIdAndNameNotEditableException("Project ID and Name are not editable");
        } else if (!issue.getProjectId().equals(updatedIssueDTO.getProjectId())) {
            throw new ProjectIdAndNameNotEditableException("Project ID is not editable");
        } else if (!issue.getProjectName().equals(updatedIssueDTO.getProjectName())) {
            throw new ProjectIdAndNameNotEditableException("Project Name is not editable");
        }

            IssueEntity updatedIssue = convertToEntity(updatedIssueDTO);
            updatedIssue.setId(issue.getId());

        if (!issue.getStatus().equals(updatedIssueDTO.getStatus()) && updatedIssueDTO.getStatus()!=Status.DONE) {
            issueActivityService.logActivity(ActivityType.STATUS_TRANSITION,
                    "Transitioned from '" + issue.getStatus() + "' to '" + updatedIssueDTO.getStatus() + "'",
                    updatedIssueDTO.getReporter(), issue.getProjectId());
        }

        if (!issue.getPriority().equals(updatedIssueDTO.getPriority())) {
            issueActivityService.logActivity(ActivityType.FIELD_CHANGED,
                    "Priority changed to " + updatedIssueDTO.getPriority(),
                    updatedIssueDTO.getReporter(), issue.getProjectId());
        }

        if (!issue.getAssignee().equals(updatedIssueDTO.getAssignee())) {
            issueActivityService.logActivity(ActivityType.FIELD_CHANGED,
                    "Assignee changed to " + updatedIssueDTO.getAssignee(),
                    updatedIssueDTO.getReporter(), issue.getProjectId());
        }

        if (Status.DONE.equals(updatedIssueDTO.getStatus()) && !issue.getStatus().equals(updatedIssueDTO.getStatus())) {
            issueActivityService.logActivity(ActivityType.ISSUE_RESOLVED,
                    "Issue resolved as '" + updatedIssueDTO.getStatus() + "'",
                    updatedIssueDTO.getReporter(), issue.getProjectId());
        }

        IssueEntity savedIssue = issueRepository.save(updatedIssue);
        return convertToDTO(savedIssue);
    });

}

    public List<IssueDTO> getAllProjects() {
        return issueRepository.findAll().stream()
                .map(this::convertToDTOWithSubtasksAndComments)
                .collect(Collectors.toList());
    }


    private IssueDTO convertToDTOWithSubtasksAndComments(IssueEntity issue) {
        IssueDTO issueDTO = convertToDTO(issue);
        List<SubtaskDTO> subtasks = subtaskRepository.findByIssueId(issue.getProjectId()).stream()
                .map(subtaskService::convertSubtaskToDTO)
                .collect(Collectors.toList());
        issueDTO.setSubtasks(subtasks);

        List<CommentDTO> comments = commentService.getCommentsByProjectId(issue.getProjectId()); // Fetch comments
        issueDTO.setComments(comments); // Set comments

        List<IssueActivityDTO> activities = issueActivityService.getAllIssueActivity(issue.getProjectId());
        activities.sort(Comparator.comparing(IssueActivityDTO::getCreatedDate).reversed());
        issueDTO.setIssueActivities(activities);

        List<Object> all= new ArrayList<>();
        all.addAll(comments);
        all.addAll(activities);

        issueDTO.setAll(all);
        return issueDTO;
    }


    public List<IssueDTO> getIssuesByAssignee(String assignee) {
        return issueRepository.findByAssignee(assignee).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private IssueEntity convertToEntity(IssueDTO issueDTO) {
        IssueEntity issue = new IssueEntity();
        issue.setId(issueDTO.getId());
        issue.setDescription(issueDTO.getDescription());
        issue.setIssueType(issueDTO.getIssueType());
        issue.setPriority(issueDTO.getPriority());
        issue.setSummary(issueDTO.getSummary());
        issue.setLabels(issueDTO.getLabels());
        issue.setStoryPoints(issueDTO.getStoryPoints());
        issue.setAssignee(issueDTO.getAssignee());
        issue.setProjectName(issueDTO.getProjectName());
        issue.setProjectId(issueDTO.getProjectId());
        issue.setSprint(issueDTO.getSprint());
        issue.setReporter(issueDTO.getReporter());
        issue.setCreatedDate(issueDTO.getCreatedDate());
        issue.setModifiedDate(issueDTO.getModifiedDate());
        issue.setStatus(issueDTO.getStatus());
        issue.setStatusLabel(issueDTO.getStatusLabel());
        return issue;
    }

    private IssueDTO convertToDTO(IssueEntity issue) {
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId(issue.getId());
        issueDTO.setDescription(issue.getDescription());
        issueDTO.setIssueType(issue.getIssueType());
        issueDTO.setPriority(issue.getPriority());
        issueDTO.setSummary(issue.getSummary());
        issueDTO.setLabels(issue.getLabels());
        issueDTO.setStoryPoints(issue.getStoryPoints());
        issueDTO.setAssignee(issue.getAssignee());
        issueDTO.setProjectName(issue.getProjectName());
        issueDTO.setProjectId(issue.getProjectId());
        issueDTO.setSprint(issue.getSprint());
        issueDTO.setReporter(issue.getReporter());
        issueDTO.setCreatedDate(issue.getCreatedDate());
        issueDTO.setModifiedDate(issue.getModifiedDate());
        issueDTO.setStatus(issue.getStatus());
        issueDTO.setStatusLabel(issue.getStatusLabel());
        return issueDTO;
    }
}
