package com.mnj.mobile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mnj.mobile.dto.TaskDTO;
import com.mnj.mobile.service.IssueService;
import com.mnj.mobile.util.CommonConst;
import com.mnj.mobile.util.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/issue")
@CrossOrigin("*")
public class IssueController {

    private IssueService issueService;

    @PostMapping
    public ResponseEntity<CommonResponse> createIssue(@RequestParam(value = "files", required = false) MultipartFile[] file,
                                                       @RequestParam("issue") String issue) {
        log.info("IssueController::createIssue dto {}", issue);
        CommonResponse commonResponse = new CommonResponse();
        try {
            String response = issueService.createIssue(file, issue);

            if (!response.equals("success.")) {
                commonResponse.setErrorMessages(Collections.singletonList("Failed ! Please try again"));
                commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            } else {
                commonResponse.setStatus(CommonConst.SUCCESS_CODE);
                commonResponse.setPayload(Collections.singletonList(response));
            }
            log.info("IssueController::createIssue response {}", HttpStatus.OK.value());
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("IssueController:createIssue error {}", ex.getMessage());
            return new ResponseEntity<>(new CommonResponse(CommonConst.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
