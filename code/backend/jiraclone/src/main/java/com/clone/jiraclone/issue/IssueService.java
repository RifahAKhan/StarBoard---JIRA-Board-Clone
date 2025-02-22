package com.clone.jiraclone.issue;

import com.clone.jiraclone.exception.ProjectIdAndNameNotEditableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.clone.jiraclone.exception.ProjectAlreadyExistsException;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public IssueEntity createIssue(IssueEntity issue) {
        Optional<IssueEntity> existingIssue = issueRepository.findByProjectId(issue.getProjectId());
        if (existingIssue.isPresent()) {
            throw new ProjectAlreadyExistsException("Project with the same id already exists");
        }
        IssueEntity newIssue = IssueEntity.builder()
                .description(issue.getDescription())
                .issueType(issue.getIssueType())
                .priority(issue.getPriority())
                .summary(issue.getSummary())
                .labels(issue.getLabels())
                .storyPoints(issue.getStoryPoints())
                .assignee(issue.getAssignee())
                .projectName(issue.getProjectName())
                .projectId(issue.getProjectId())
                .sprint(issue.getSprint())
                .reporter(issue.getReporter())
                .build();
        return issueRepository.save(newIssue);
    }
    public Optional<IssueEntity> getProjectById(Long id) {
        return issueRepository.findById(id);
    }
    public Optional<IssueEntity> updateIssue(Long id, IssueEntity updatedIssue) {
        return issueRepository.findById(id).map(issue -> {
            if (!issue.getProjectId().equals(updatedIssue.getProjectId()) && !issue.getProjectName().equals(updatedIssue.getProjectName())) {
                throw new ProjectIdAndNameNotEditableException("Project ID and Name are not editable");
            }
            else if (!issue.getProjectId().equals(updatedIssue.getProjectId())) {
                throw new ProjectIdAndNameNotEditableException("Project ID is not editable");
            }
            else if (!issue.getProjectName().equals(updatedIssue.getProjectName())) {
                throw new ProjectIdAndNameNotEditableException("Project Name is not editable");
            }
            issue.setDescription(updatedIssue.getDescription());
            issue.setIssueType(updatedIssue.getIssueType());
            issue.setPriority(updatedIssue.getPriority());
            issue.setSummary(updatedIssue.getSummary());
            issue.setLabels(updatedIssue.getLabels());
            issue.setStoryPoints(updatedIssue.getStoryPoints());
            issue.setAssignee(updatedIssue.getAssignee());
            issue.setProjectName(updatedIssue.getProjectName());
            issue.setSprint(updatedIssue.getSprint());
            issue.setReporter(updatedIssue.getReporter());
            return issueRepository.save(issue);
        });
    }
    public List<IssueEntity> getAllProjects() {
        return issueRepository.findAll();
    }
    public List<IssueEntity> getIssuesByAssignee(String assignee) {
        return issueRepository.findByAssignee(assignee);
    }
}
