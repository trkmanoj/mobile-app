package com.mnj.mobile.controller;

import com.mnj.mobile.dto.AuthenticationRequest;
import com.mnj.mobile.dto.AuthenticationResponse;
import com.mnj.mobile.dto.UserDTO;
import com.mnj.mobile.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
@Slf4j
public class AuthenticationController {


    private AuthenticationService authenticationService;


    /**
     * Generate authentication token based on user credentials
     * @param authenticationRequest
     * @return AuthenticationResponse
     */
    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    /**
     * This method use for create user
     * @param user
     * @return ResponseEntity
     * @throws Exception
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user) {
        log.info("AuthenticationController::register");
        try {
            AuthenticationResponse response = authenticationService.register(user);

            if (response == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username already exist.");
            }else {
                log.info("AuthenticationController::register response {}",response.getUsername());
                return ResponseEntity.ok(authenticationService.register(user));
            }

        }catch (Exception ex){
            log.info("AuthenticationController::register error {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
