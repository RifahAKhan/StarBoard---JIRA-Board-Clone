package com.clone.jiraclone.issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public IssueEntity createIssue(IssueEntity issue) {
        IssueEntity newIssue = IssueEntity.builder()
                .description(issue.getDescription())
                .issueType(issue.getIssueType())
                .priority(issue.getPriority())
                .summary(issue.getSummary())
                .labels(issue.getLabels())
                .storyPoints(issue.getStoryPoints())
                .assignee(issue.getAssignee())
                .project(issue.getProject())
                .build();
        return issueRepository.save(newIssue);
    }
    public Optional<IssueEntity> getProjectById(Long id) {
        return issueRepository.findById(id);
    }
    public Optional<IssueEntity> updateIssue(Long id, IssueEntity updatedIssue) {
        return issueRepository.findById(id).map(issue -> {
            issue.setDescription(updatedIssue.getDescription());
            issue.setIssueType(updatedIssue.getIssueType());
            issue.setPriority(updatedIssue.getPriority());
            issue.setSummary(updatedIssue.getSummary());
            issue.setLabels(updatedIssue.getLabels());
            issue.setStoryPoints(updatedIssue.getStoryPoints());
            issue.setAssignee(updatedIssue.getAssignee());
            issue.setProject(updatedIssue.getProject());
            return issueRepository.save(issue);
        });
    }
}
