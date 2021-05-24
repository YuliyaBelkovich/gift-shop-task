package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.service.auth.UserDetailsImpl;
import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.dto.response.UserResponse;
import com.epam.esm.exception.ExceptionDefinition;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.models.*;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    @Override
    public UserResponse findById(int id) {
        return UserResponse.toDto(userDao.findById(id)
                .orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
    }

    @Override
    public PageableResponse<UserResponse> findAll(int page, int pageSize) {
        PageableResponse<User> users = userDao.findAll(page, pageSize);
        List<UserResponse> responses = users.getResponses().stream()
                .map(UserResponse::toDto).collect(Collectors.toList());
        return new PageableResponse<>(responses,
                users.getCurrentPage(),
                users.getLastPage(),
                users.getPageSize(),
                users.getTotalElements());
    }

    @Override
    public PageableResponse<OrderResponse> findOrdersByUserId(int id, int page, int pageSize) {
        PageableResponse<Order> orders = userDao.findUserOrders(id, page, pageSize);
        List<OrderResponse> responses = orders.getResponses().stream()
                .map(OrderResponse::toDto).collect(Collectors.toList());
        return new PageableResponse<>(responses,
                orders.getCurrentPage(),
                orders.getLastPage(),
                orders.getPageSize(),
                orders.getTotalElements());
    }

    @Override
    public OrderResponse findOrderById(int userId, int orderId) {
        return OrderResponse.toDto(userDao.findUserOrderById(userId, orderId)
                .orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public User findByName(ERole name) {
        return userDao.findByName(name.name()).orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND));
    }


}
