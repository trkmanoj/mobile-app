package com.mnj.mobile.service.impl;

import com.mnj.mobile.dto.AuthenticationRequest;
import com.mnj.mobile.dto.AuthenticationResponse;
import com.mnj.mobile.dto.UserDTO;
import com.mnj.mobile.entity.User;
import com.mnj.mobile.enums.Role;
import com.mnj.mobile.repository.UserRepository;
import com.mnj.mobile.service.AuthenticationService;
import com.mnj.mobile.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private JwtUtil jwtUtil;


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        log.info("AuthenticationServiceImpl:authenticate execution started.");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        User user = userRepository.findByUsername(authenticationRequest.getUsername());
        String token = jwtUtil.generateToken(user);

        log.info("AuthenticationServiceImpl:authenticate execution ended.");
        return mapToResponse(user, token);
    }

    @Override
    public AuthenticationResponse register(UserDTO request) {
        log.info("AuthenticationServiceImpl:register execution started.");

        User user = userRepository.save(
                new User(
                        null,
                        request.getFirstName(),
                        request.getLastName(),
                        request.getUsername(),
                        passwordEncoder.encode(request.getPassword()),
                        Role.ADMIN,
                        LocalDateTime.now(),
                        LocalDateTime.now())
        );

        String token = jwtUtil.generateToken(user);

        log.info("AuthenticationServiceImpl:register execution ended.");
        return mapToResponse(user,token);
    }


    private AuthenticationResponse mapToResponse(User user, String token){
        return new AuthenticationResponse(
                token,
                user.getFirstName(),
                user.getLastName(),
                user.getUsername());
    }
}
