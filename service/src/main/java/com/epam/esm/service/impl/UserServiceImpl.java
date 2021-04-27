package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.dto.response.UserResponse;
import com.epam.esm.exception.ExceptionDefinition;
import com.epam.esm.exception.ServiceException;
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
    public List<UserResponse> findAll() {
        return userDao.findAll().stream().map(UserResponse::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> findOrdersByUserId(int id) {
        return userDao.findUserOrders(id).stream().map(OrderResponse::toDto).collect(Collectors.toList());
    }

    @Override
    public OrderResponse findOrderById(int userId, int orderId) {
        return OrderResponse.toDto(userDao.findUserOrderById(userId,orderId).orElseThrow(()->new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
    }


}
