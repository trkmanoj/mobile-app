package com.mnj.mobile.service;

import com.mnj.mobile.dto.CommentDTO;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    String createComment(CommentDTO dto);

    List<CommentDTO> findCommentsByIssue(UUID issueId);

    CommentDTO findCommentById(String commentId);

    String updateComment(String commentId, String description);
}
