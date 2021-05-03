package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.dto.response.UserResponse;
import com.epam.esm.models.Order;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl service;

    @Mock
    UserDao userDao;

    @Mock
    OrderDao orderDao;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        User testData = User.builder()
                .setId(1)
                .setName("test name")
                .setEmail("test email")
                .setPassword("test password")
                .setOrders(new ArrayList<>())
                .build();

        UserResponse expected = UserResponse.toDto(testData);

        Mockito.when(userDao.findById(testData.getId())).thenReturn(Optional.of(testData));

        UserResponse actual = service.findById(1);

        assertEquals(actual, expected);
    }

    @Test
    void findAll() {
        User testData = User.builder()
                .setId(1)
                .setName("test name")
                .setEmail("test email")
                .setPassword("test password")
                .setOrders(new ArrayList<>())
                .build();

        List<User> userList = new ArrayList<>();
        userList.add(testData);
        List<UserResponse> expected = new ArrayList<>();
        expected.add(UserResponse.toDto(testData));

        Mockito.when(userDao.findAll(1, 1)).thenReturn(new PageableResponse<>(userList, 1, 1, 1, 1));

        List<UserResponse> actual = service.findAll(1, 1).getResponses();

        assertEquals(actual, expected);
    }

    @Test
    void findOrdersByUserId() {
        Order testData = Order.builder()
                .setId(1)
                .setCost(1)
                .setCreateDate(LocalDateTime.now())
                .setUser(User.builder().build())
                .setCertificates(new ArrayList<>())
                .build();

        List<OrderResponse> expected = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        orders.add(testData);
        expected.add(OrderResponse.toDto(testData));

        Mockito.when(userDao.findUserOrders(1, 1, 1)).thenReturn(new PageableResponse<>(orders, 1, 1, 1, 1));

        List<OrderResponse> actual = service.findOrdersByUserId(1, 1, 1).getResponses();

        assertEquals(expected, actual);

    }

    @Test
    void findOrderById() {
        Order testData = Order.builder()
                .setId(1)
                .setCost(1)
                .setCreateDate(LocalDateTime.now())
                .setUser(User.builder().build())
                .setCertificates(new ArrayList<>())
                .build();

        OrderResponse expected = OrderResponse.toDto(testData);

        Mockito.when(userDao.findUserOrderById(1, 1)).thenReturn(Optional.of(testData));

        OrderResponse actual = service.findOrderById(1, 1);

        assertEquals(expected, actual);
    }
}