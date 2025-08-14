package com.mnj.mobile.service;

import com.mnj.mobile.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAllUsers();
}
