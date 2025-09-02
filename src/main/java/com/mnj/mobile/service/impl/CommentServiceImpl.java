package com.mnj.mobile.service.impl;

import com.mnj.mobile.dto.CommentDTO;
import com.mnj.mobile.entity.Comment;
import com.mnj.mobile.repository.CommentRepository;
import com.mnj.mobile.repository.IssueRepository;
import com.mnj.mobile.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

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
}
