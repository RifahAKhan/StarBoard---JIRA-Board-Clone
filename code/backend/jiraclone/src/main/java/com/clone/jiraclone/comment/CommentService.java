package com.clone.jiraclone.comment;

import com.clone.jiraclone.exception.CommentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public CommentDTO addComment(CommentDTO commentDTO) {
        CommentEntity comment = convertToEntity(commentDTO);
        comment.setCreatedDate(LocalDateTime.now());
        CommentEntity savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Optional<CommentEntity> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            CommentEntity comment = optionalComment.get();
            comment.setCommentText(commentDTO.getCommentText());
            comment.setAuthor(commentDTO.getAuthor());
            comment.setProjectId(commentDTO.getProjectId());
            comment.setCreatedDate(commentDTO.getCreatedDate());
            CommentEntity updatedComment = commentRepository.save(comment);
            return convertToDTO(updatedComment);
        } else {
            throw new CommentNotFoundException("Comment with id " + id + " not found");
        }
    }
    public List<CommentDTO> getCommentsByProjectId(Long projectId) {
        return commentRepository.findByProjectId(projectId).stream()
                .map(this::convertToDTO) // Convert CommentEntity to CommentDTO
                .collect(Collectors.toList());
    }

    private CommentEntity convertToEntity(CommentDTO commentDTO) {
        CommentEntity comment = new CommentEntity();
        comment.setCommentText(commentDTO.getCommentText());
        comment.setAuthor(commentDTO.getAuthor());
        comment.setProjectId(commentDTO.getProjectId());
        return comment;
    }

    private CommentDTO convertToDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentText(commentEntity.getCommentText());
        commentDTO.setAuthor(commentEntity.getAuthor());
        commentDTO.setProjectId(commentEntity.getProjectId());
        commentDTO.setCreatedDate(commentEntity.getCreatedDate());
        return commentDTO;
    }

}