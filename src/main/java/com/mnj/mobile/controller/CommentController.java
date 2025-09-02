package com.mnj.mobile.controller;

import com.mnj.mobile.dto.CommentDTO;
import com.mnj.mobile.service.CommentService;
import com.mnj.mobile.util.CommonConst;
import com.mnj.mobile.util.CommonResponse;
import com.mnj.mobile.util.ValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/comment")
@CrossOrigin("*")
public class CommentController {

    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommonResponse> createComment(@RequestBody CommentDTO dto) {
        log.info("CommentController::createComment dto {}", ValueMapper.jsonAsString(dto));
        CommonResponse commonResponse = new CommonResponse();
        try {
            String response = commentService.createComment(dto);

            if (!response.equals("success.")) {
                commonResponse.setErrorMessages(Collections.singletonList("Failed ! Please try again"));
                commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            } else {
                commonResponse.setStatus(CommonConst.SUCCESS_CODE);
                commonResponse.setPayload(Collections.singletonList(response));
            }
            log.info("CommentController::createComment response {}", HttpStatus.OK.value());
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("CommentController:createComment error {}", ex.getMessage());
            return new ResponseEntity<>(new CommonResponse(CommonConst.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<CommonResponse> findCommentsByIssue(@PathVariable("issueId") String issueId){
        log.info("IssueController::findCommentsByIssue issueId {}", issueId);
        CommonResponse commonResponse = new CommonResponse();

        try{

            List<CommentDTO> response = commentService.findCommentsByIssue(issueId);

            if (response.isEmpty()) {
                commonResponse.setErrorMessages(Collections.singletonList("Not found records."));
                commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            } else {
                commonResponse.setStatus(CommonConst.SUCCESS_CODE);
                commonResponse.setPayload(Collections.singletonList(response));
            }
            log.info("IssueController::findCommentsByIssue response {}", HttpStatus.OK.value());
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);

        }catch (Exception ex){
            log.error("IssueController:findCommentsByIssue error {}", ex.getMessage());
            return new ResponseEntity<>(new CommonResponse(CommonConst.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
