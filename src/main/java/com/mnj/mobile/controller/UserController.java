package com.mnj.mobile.controller;

import com.mnj.mobile.dto.UserResponse;
import com.mnj.mobile.service.UserService;
import com.mnj.mobile.util.CommonConst;
import com.mnj.mobile.util.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<CommonResponse> findAllUsers() {
        log.info("UserController::findAllUsers");
        CommonResponse commonResponse = new CommonResponse();
        try {

            List<UserResponse> response = userService.findAllUsers();

            if (response.isEmpty()) {
                log.info("UserController::findAllUsers not found records.");
                commonResponse.setErrorMessages(Collections.singletonList("Not found records."));
                commonResponse.setStatus(CommonConst.NOT_FOUND_RECORD);
            } else {
                commonResponse.setStatus(CommonConst.SUCCESS_CODE);
                commonResponse.setPayload(Collections.singletonList(response));
            }
            log.info("UserController::findAllUsers response {}", HttpStatus.OK.value());
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("UserController:findAllUsers error {}", ex.getMessage());
            return new ResponseEntity<>(new CommonResponse(CommonConst.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
