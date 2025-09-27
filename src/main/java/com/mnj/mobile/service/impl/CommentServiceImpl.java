package com.mnj.mobile.service.impl;

import com.mnj.mobile.dto.CommentDTO;
import com.mnj.mobile.entity.Comment;
import com.mnj.mobile.repository.CommentRepository;
import com.mnj.mobile.repository.IssueRepository;
import com.mnj.mobile.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    private IssueRepository issueRepository;

    @Override
    public String createComment(CommentDTO dto) {
        log.info("CommentServiceImpl:createComment execution started.");

        commentRepository.save(new Comment(
                dto.getId(),
                dto.getDescription(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                issueRepository.findById(UUID.fromString(dto.getIssue())).get(),
                dto.isStatus()
        ));

        log.info("CommentServiceImpl:createComment execution ended.");
        return "success.";
    }

    @Override
    public List<CommentDTO> findCommentsByIssue(UUID issueId) {
        log.info("CommentServiceImpl:findCommentsByIssue execution started.");

        List<Comment> comments = commentRepository.findByIssueIssueId(issueId);

        List<CommentDTO> list = comments.stream().map(comment -> new CommentDTO(
                comment.getId(),
                comment.getDescription(),
                comment.getCreatedTime(),
                comment.getModifiedTime(),
                comment.isStatus()
        )).collect(Collectors.toList());

        log.info("CommentServiceImpl:findCommentsByIssue execution ended.");
        return list;
    }

    @Override
    public CommentDTO findCommentById(String commentId) {
        log.info("CommentServiceImpl:findCommentById execution started.");

        if (!issueRepository.existsById(UUID.fromString(commentId)))
            return null;


        Comment comment = commentRepository.findById(UUID.fromString(commentId)).get();

        CommentDTO commentDTO = new CommentDTO(
                comment.getId(),
                comment.getDescription(),
                comment.getCreatedTime(),
                comment.getModifiedTime(),
                comment.isStatus()
        );

        log.info("CommentServiceImpl:findCommentById execution ended.");
        return commentDTO;
    }

    @Transactional
    @Override
    public String updateComment(String commentId, String description) {
        log.info("CommentServiceImpl:updateComment execution started.");

        commentRepository.updateComment(commentId, description, LocalDateTime.now());

/*
        Comment comment = commentRepository.findById(UUID.fromString(commentId)).get();

        Comment commentNew = comment.toBuilder()
                .description(description)
                .modifiedTime(LocalDateTime.now())
                .build();

        commentRepository.save(commentNew);
*/

        log.info("CommentServiceImpl:updateComment execution end.");
        return "success.";
    }
}
