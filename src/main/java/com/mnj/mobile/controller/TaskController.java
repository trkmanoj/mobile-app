package com.mnj.mobile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mnj.mobile.dto.ProjectDTO;
import com.mnj.mobile.dto.TaskDTO;
import com.mnj.mobile.service.TaskService;
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
@RequestMapping("/api/v1/task")
@CrossOrigin("*")
public class TaskController {

    private TaskService taskService;

    @PostMapping("/save")
    public ResponseEntity<CommonResponse> createTask(@RequestParam(value = "files",required = false) MultipartFile[] files, @RequestParam("task") String task) throws JsonProcessingException {
        log.info("TaskController::createTask task {}", task);
        CommonResponse commonResponse = new CommonResponse();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        TaskDTO projectO = mapper.readValue(task, TaskDTO.class);
        try {
            String response = taskService.createTask(files, projectO);

            if (!response.equals("success.")) {
                commonResponse.setErrorMessages(Collections.singletonList("Failed ! Please try again"));
                commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            } else {
                commonResponse.setStatus(CommonConst.SUCCESS_CODE);
                commonResponse.setPayload(Collections.singletonList(response));
            }
            log.info("TaskController::createTask response {}", HttpStatus.OK.value());
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("TaskController:createTask error {}", ex.getMessage());
            return new ResponseEntity<>(new CommonResponse(CommonConst.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<CommonResponse> findByProject(@PathVariable("projectId") String projectId) {
        log.info("TaskController::findByProject projectId {}", projectId);
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<TaskDTO> taskDTOS = taskService.findByProject(projectId);

            if (taskDTOS.isEmpty()) {
                commonResponse.setErrorMessages(Collections.singletonList("Not found records."));
                commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            } else {
                commonResponse.setStatus(CommonConst.SUCCESS_CODE);
                commonResponse.setPayload(Collections.singletonList(taskDTOS));
            }
            log.info("TaskController::findByProject response {}", HttpStatus.OK.value());
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("TaskController:findByProject error {}", ex.getMessage());
            return new ResponseEntity<>(new CommonResponse(CommonConst.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
