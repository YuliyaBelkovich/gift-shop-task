package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.dto.response.UserResponse;
import com.epam.esm.exception.ExceptionDefinition;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.models.Order;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.models.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private OrderDao orderDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, OrderDao orderDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
    }

    @Override
    public UserResponse findById(int id) {
        return UserResponse.toDto(userDao.findById(id).orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
    }

    @Override
    public PageableResponse<UserResponse> findAll(int page, int pageSize) {
        PageableResponse<User> users = userDao.findAll(page, pageSize);
        List<UserResponse> responses = users.getResponses().stream().map(UserResponse::toDto).collect(Collectors.toList());
        return new PageableResponse<>(responses, users.getCurrentPage(), users.getLastPage(), users.getPageSize());
    }

    @Override
    public PageableResponse<OrderResponse> findOrdersByUserId(int id, int page, int pageSize) {
        PageableResponse<Order> orders = userDao.findUserOrders(id, page, pageSize);
        List<OrderResponse> responses = orders.getResponses().stream().map(OrderResponse::toDto).collect(Collectors.toList());
        return new PageableResponse<>(responses, orders.getCurrentPage(), orders.getLastPage(), orders.getPageSize());
    }

    @Override
    public OrderResponse findOrderById(int userId, int orderId) {
        return OrderResponse.toDto(userDao.findUserOrderById(userId, orderId).orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
    }


}
