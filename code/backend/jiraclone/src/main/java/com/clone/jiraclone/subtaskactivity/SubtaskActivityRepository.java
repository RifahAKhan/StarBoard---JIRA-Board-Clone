package com.clone.jiraclone.subtaskactivity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtaskActivityRepository extends JpaRepository<SubtaskActivityEntity, Long> {

    List<SubtaskActivityEntity> findByIssueId(Long subtaskId);
}
