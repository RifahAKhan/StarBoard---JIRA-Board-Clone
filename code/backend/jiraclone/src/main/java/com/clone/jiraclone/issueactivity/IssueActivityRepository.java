package com.clone.jiraclone.issueactivity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueActivityRepository extends JpaRepository<IssueActivityEntity, Long> {

    List<IssueActivityEntity> findByIssueId(Long issueId);

}
