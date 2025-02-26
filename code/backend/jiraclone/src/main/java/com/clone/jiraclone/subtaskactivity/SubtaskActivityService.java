package com.clone.jiraclone.subtaskactivity;

import com.clone.jiraclone.issueactivity.IssueActivityEntity;
import com.clone.jiraclone.utils.ActivityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubtaskActivityService {

    @Autowired
    private SubtaskActivityRepository subtaskActivityRepository;

    private SubtaskActivityEntity convertToEntity(SubtaskActivityDTO subtaskActivityDTO) {
        SubtaskActivityEntity subtaskActivityEntity = new SubtaskActivityEntity();
        subtaskActivityEntity.setId(subtaskActivityDTO.getId());
        subtaskActivityEntity.setIssueId(subtaskActivityDTO.getIssueId());
        subtaskActivityEntity.setDescription(subtaskActivityDTO.getDescription());
        subtaskActivityEntity.setType(ActivityType.valueOf(String.valueOf(subtaskActivityDTO.getType())));
        subtaskActivityEntity.setUserName(subtaskActivityDTO.getUserName());
        subtaskActivityEntity.setTimestamp(subtaskActivityDTO.getTimestamp());
        return subtaskActivityEntity;
    }

    public SubtaskActivityDTO convertSubtaskActivityToDTO(SubtaskActivityEntity subtaskActivityEntity) {
        SubtaskActivityDTO subtaskActivityDTO = new SubtaskActivityDTO();
        subtaskActivityDTO.setId(subtaskActivityEntity.getId());
        subtaskActivityDTO.setIssueId(subtaskActivityEntity.getIssueId());
        subtaskActivityDTO.setDescription(subtaskActivityEntity.getDescription());
        subtaskActivityDTO.setType(ActivityType.valueOf(subtaskActivityEntity.getType().name()));
        subtaskActivityDTO.setUserName(subtaskActivityEntity.getUserName());
        subtaskActivityDTO.setTimestamp(subtaskActivityEntity.getTimestamp());
        return subtaskActivityDTO;
    }
    public List<SubtaskActivityDTO> getAllSubtaskActivity(Long id) {
        return subtaskActivityRepository.findByIssueId(id).stream()
                .map(this::convertSubtaskActivityToDTO)
                .collect(Collectors.toList());
    }

    public void logActivity(ActivityType type, String description, String userName, Long issueId) {
        SubtaskActivityEntity activity = new SubtaskActivityEntity();
        activity.setType(type);
        activity.setDescription(description);
        activity.setUserName(userName);
        activity.setTimestamp(LocalDateTime.now());
        activity.setIssueId(issueId);
        subtaskActivityRepository.save(activity);
    }

}
