package com.mnj.mobile.service;

import com.mnj.mobile.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    String createComment(CommentDTO dto);

    List<CommentDTO> findCommentsByIssue(String issueId);
}
