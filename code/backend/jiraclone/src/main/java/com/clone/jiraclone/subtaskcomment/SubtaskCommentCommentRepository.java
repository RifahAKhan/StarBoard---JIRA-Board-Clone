package com.clone.jiraclone.subtaskcomment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtaskCommentCommentRepository extends JpaRepository<SubtaskCommentEntity, Long> {
    List<SubtaskCommentEntity> findBySubtaskId(Long subtaskId);
}
