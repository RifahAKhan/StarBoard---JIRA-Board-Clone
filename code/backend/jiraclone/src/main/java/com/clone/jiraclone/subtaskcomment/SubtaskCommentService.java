package com.clone.jiraclone.subtaskcomment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubtaskCommentService {

    @Autowired
    private SubtaskCommentCommentRepository commentRepository;

    public SubtaskCommentDTO createComment(SubtaskCommentDTO commentDTO,Long subtaskId) {
        SubtaskCommentEntity comment = convertToEntity(commentDTO);
        comment.setSubtaskId(subtaskId);
        SubtaskCommentEntity savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    public Optional<SubtaskCommentDTO> updateComment(Long id, SubtaskCommentDTO updatedCommentDTO) {
        return commentRepository.findById(id).map(comment -> {
            comment.setContent(updatedCommentDTO.getContent());
            comment.setUpdatedBy(updatedCommentDTO.getUpdatedBy());
            comment.setUpdatedDate(updatedCommentDTO.getUpdatedDate());
            SubtaskCommentEntity savedComment = commentRepository.save(comment);
            return convertToDTO(savedComment);
        });
    }

    public List<SubtaskCommentDTO> getCommentsBySubtaskId(Long subtaskId) {
        return commentRepository.findBySubtaskId(subtaskId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SubtaskCommentEntity convertToEntity(SubtaskCommentDTO commentDTO) {
        SubtaskCommentEntity comment = new SubtaskCommentEntity();
        comment.setId(commentDTO.getId());
        comment.setContent(commentDTO.getContent());
        comment.setCreatedBy(commentDTO.getCreatedBy());
        comment.setCreatedDate(commentDTO.getCreatedDate());
        comment.setUpdatedBy(commentDTO.getUpdatedBy());
        comment.setUpdatedDate(commentDTO.getUpdatedDate());
        return comment;
    }

    private SubtaskCommentDTO convertToDTO(SubtaskCommentEntity comment) {
        SubtaskCommentDTO commentDTO = new SubtaskCommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setSubtaskId(comment.getSubtaskId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatedBy(comment.getCreatedBy());
        commentDTO.setCreatedDate(comment.getCreatedDate());
        commentDTO.setUpdatedBy(comment.getUpdatedBy());
        commentDTO.setUpdatedDate(comment.getUpdatedDate());
        return commentDTO;
    }
}
