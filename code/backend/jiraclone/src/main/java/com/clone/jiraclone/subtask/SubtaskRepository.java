package com.clone.jiraclone.subtask;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtaskRepository extends JpaRepository<SubtaskEntity, Long> {
    List<SubtaskEntity> findByIssueId(Long issueId);
}
