package com.mnj.mobile.service;

import com.mnj.mobile.dto.AuthenticationRequest;
import com.mnj.mobile.dto.AuthenticationResponse;
import com.mnj.mobile.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    AuthenticationResponse register(UserDTO user);
}
