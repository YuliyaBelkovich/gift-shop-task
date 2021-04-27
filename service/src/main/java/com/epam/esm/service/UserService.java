package com.epam.esm.service;

import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse findById(int id);

    List<UserResponse> findAll();

    List<OrderResponse> findOrdersByUserId(int id);

    OrderResponse findOrderById(int userId, int orderId);

}
