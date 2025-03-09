package com.clone.jiraclone.issueactivity;

import com.clone.jiraclone.utils.ActivityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueActivityService {

    @Autowired
    private IssueActivityRepository issueActivityRepository;


    private IssueActivityEntity convertToEntity(IssueActivityDTO issueActivityDTO) {
        IssueActivityEntity issueActivityEntity = new IssueActivityEntity();
        issueActivityEntity.setId(issueActivityDTO.getId());
        issueActivityEntity.setIssueId(issueActivityDTO.getIssueId());
        issueActivityEntity.setDescription(issueActivityDTO.getDescription());
        issueActivityEntity.setType(ActivityType.valueOf(String.valueOf(issueActivityDTO.getType())));
        issueActivityEntity.setUserName(issueActivityDTO.getUserName());
        issueActivityEntity.setTimestamp(issueActivityDTO.getCreatedDate());
        return issueActivityEntity;
    }

    public IssueActivityDTO convertIssueActivityToDTO(IssueActivityEntity issueActivity) {
        IssueActivityDTO issueActivityDTO = new IssueActivityDTO();
        issueActivityDTO.setId(issueActivity.getId());
        issueActivityDTO.setIssueId(issueActivity.getIssueId());
        issueActivityDTO.setDescription(issueActivity.getDescription());
        issueActivityDTO.setType(ActivityType.valueOf(issueActivity.getType().name()));
        issueActivityDTO.setUserName(issueActivity.getUserName());
        issueActivityDTO.setCreatedDate(issueActivity.getTimestamp());
        return issueActivityDTO;
    }
//    public List<IssueActivityDTO> getAllIssueActivity(Long issueId) {
//        return issueActivityRepository.findAll().stream()
//                .map(this::convertIssueActivityToDTO)
//                .collect(Collectors.toList());
//    }

    public List<IssueActivityDTO> getAllIssueActivity(Long issueId) {
        return issueActivityRepository.findByIssueId(issueId).stream()
                .map(this::convertIssueActivityToDTO)
                .collect(Collectors.toList());
    }

    public void logActivity(ActivityType type, String description, String userName, Long issueId) {
        IssueActivityEntity activity = new IssueActivityEntity();
        activity.setType(type);
        activity.setDescription(description);
        activity.setUserName(userName);
        activity.setTimestamp(LocalDateTime.now());
        activity.setIssueId(issueId);
        issueActivityRepository.save(activity);
    }
}
