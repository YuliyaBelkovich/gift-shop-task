package com.epam.esm.service;

import com.epam.esm.dto.request.LoginRequest;
import com.epam.esm.dto.request.SignupRequest;
import com.epam.esm.dto.response.JwtResponse;
import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.dto.response.UserResponse;
import com.epam.esm.models.ERole;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.models.User;

import java.util.Optional;

public interface UserService {

    UserResponse findById(int id);

    PageableResponse<UserResponse> findAll(int page, int pageSize);

    PageableResponse<OrderResponse> findOrdersByUserId(int id, int page, int pageSize);

    OrderResponse findOrderById(int userId, int orderId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findByName(ERole name);

    JwtResponse logIn(LoginRequest loginRequest);

    void register(SignupRequest signupRequest);

}
