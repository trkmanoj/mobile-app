package com.mnj.mobile.controller;

import com.mnj.mobile.dto.AuthenticationRequest;
import com.mnj.mobile.dto.AuthenticationResponse;
import com.mnj.mobile.dto.UserDTO;
import com.mnj.mobile.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
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
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDTO user) {
        return ResponseEntity.ok(authenticationService.register(user));
    }
}
