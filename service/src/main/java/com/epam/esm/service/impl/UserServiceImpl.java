package com.epam.esm.service.impl;

import com.epam.esm.dto.response.UserResponse;
import com.epam.esm.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserResponse findById(int id) {
        return null;
    }

    @Override
    public List<UserResponse> findAll() {
        return null;
    }

}
