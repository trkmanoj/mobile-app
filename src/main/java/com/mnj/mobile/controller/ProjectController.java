package com.mnj.mobile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mnj.mobile.dto.ProjectDTO;
import com.mnj.mobile.service.ProjectService;
import com.mnj.mobile.util.CommonConst;
import com.mnj.mobile.util.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/project")
@CrossOrigin("*")
public class ProjectController {

    private ProjectService projectService;
    @GetMapping("/ping")
    public String ping() {
        System.out.println("Hai");
        return "pong";
    }
    @PostMapping("/save")
    public ResponseEntity<CommonResponse> createProject(
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestParam("project") String project) {

        log.info("ProjectController::createProject projectJson {}", project);

        CommonResponse commonResponse = new CommonResponse();
        try {

            String response = projectService.createProject(files, project);

            if (!"success.".equals(response)) {
                commonResponse.setErrorMessages(Collections.singletonList("Failed! Please try again"));
                commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            } else {
                commonResponse.setStatus(CommonConst.SUCCESS_CODE);
                commonResponse.setPayload(Collections.singletonList(response));
            }

            log.info("ProjectController::createProject response {}", HttpStatus.OK.value());
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);

        } catch (Exception ex) {
            log.error("ProjectController:createProject error {}", ex.getMessage());
            return new ResponseEntity<>(new CommonResponse(CommonConst.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GetMapping("/{projectId}")
    public ResponseEntity<CommonResponse> findProjectById(@PathVariable("projectId") String projectId) {
        log.info("ProjectController::findProjectById projectId {}", projectId);
        CommonResponse commonResponse = new CommonResponse();
        try {
            ProjectDTO response = projectService.findById(projectId);

            if (response == null) {
                commonResponse.setErrorMessages(Collections.singletonList("Not Found Records."));
                commonResponse.setStatus(CommonConst.NOT_FOUND_RECORD);
            } else {
                commonResponse.setStatus(CommonConst.SUCCESS_CODE);
                commonResponse.setPayload(Collections.singletonList(response));
            }
            log.info("ProjectController::findProjectById response {}", HttpStatus.OK.value());
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("ProjectController:findProjectById error {}", ex.getMessage());
            return new ResponseEntity<>(new CommonResponse(CommonConst.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<CommonResponse> findAll() {
        log.info("ProjectController::findAll");
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<ProjectDTO> response = projectService.findAll();

            if (response.isEmpty()) {
                commonResponse.setErrorMessages(Collections.singletonList("Not Found Records."));
                commonResponse.setStatus(CommonConst.NOT_FOUND_RECORD);
            } else {
                commonResponse.setStatus(CommonConst.SUCCESS_CODE);
                commonResponse.setPayload(Collections.singletonList(response));
            }
            log.info("ProjectController::findAll response {}", HttpStatus.OK.value());
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("ProjectController:findAll error {}", ex.getMessage());
            return new ResponseEntity<>(new CommonResponse(CommonConst.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
