package com.clone.jiraclone.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity, Long> {
    Optional<IssueEntity> findByProjectId(Long projectId);

    List<IssueEntity> findByAssignee(String assignee);

    boolean existsByProjectId(Long issueId);
}
