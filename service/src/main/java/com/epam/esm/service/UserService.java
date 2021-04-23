package com.epam.esm.service;

import com.epam.esm.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse findById(int id);

    List<UserResponse> findAll();
}
