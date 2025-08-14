package com.mnj.mobile.service.impl;

import com.mnj.mobile.dto.UserResponse;
import com.mnj.mobile.repository.UserRepository;
import com.mnj.mobile.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    @Override
    public List<UserResponse> findAllUsers() {
        log.info("UserServiceImpl:findAllUsers execution started");

        List<UserResponse> userResponses = userRepository.findAll().stream()
                .map(user -> new UserResponse(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUsername(),
                        user.isStatus(),
                        user.isAccountNonLocked(),
                        user.isAccountNonExpired(),
                        user.getRole(),
                        user.getCreatedDate(),
                        user.getModifiedDate()
                )).collect(Collectors.toList());

        return userResponses;
    }

}
