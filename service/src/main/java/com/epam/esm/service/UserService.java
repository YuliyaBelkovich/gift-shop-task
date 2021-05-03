package com.epam.esm.service;

import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.dto.response.UserResponse;
import com.epam.esm.models.PageableResponse;

public interface UserService {

    UserResponse findById(int id);

    PageableResponse<UserResponse> findAll(int page, int pageSize);

    PageableResponse<OrderResponse> findOrdersByUserId(int id, int page, int pageSize);

    OrderResponse findOrderById(int userId, int orderId);

}
