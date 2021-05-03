package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.request.OrderRequest;
import com.epam.esm.dto.response.OrderResponse;
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

class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl service;

    @Mock
    OrderDao orderDao;

    @Mock
    UserDao userDao;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
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

        Mockito.when(orderDao.findAll(1, 1)).thenReturn(new PageableResponse<>(orders, 1, 1, 1, 1));

        List<OrderResponse> actual = service.findAll(1, 1).getResponses();

        assertEquals(actual, expected);
    }

    @Test
    void findById() {
        Order testData = Order.builder()
                .setId(1)
                .setCost(1)
                .setCreateDate(LocalDateTime.now())
                .setUser(User.builder().build())
                .setCertificates(new ArrayList<>())
                .build();

        OrderResponse expected = OrderResponse.toDto(testData);

        Mockito.when(orderDao.findById(testData.getId())).thenReturn(Optional.of(testData));

        OrderResponse actual = service.findById(1);

        assertEquals(actual, expected);
    }

    @Test
    void save() {
        Order testData = Order.builder()
                .setId(0)
                .setCost(0)
                .setUser(User.builder().setId(1).build())
                .setCertificates(new ArrayList<>())
                .build();

        Mockito.when(userDao.findById(1)).thenReturn(Optional.of(User.builder().setId(1).build()));

        Mockito.when(orderDao.findById(testData.getId())).thenReturn(Optional.of(testData));

        service.save(OrderRequest.toDto(testData));

        Mockito.verify(orderDao).add(testData);
    }

    @Test
    void delete() {
        Order testData = Order.builder()
                .setId(1)
                .setCost(1)
                .setCreateDate(LocalDateTime.now())
                .setUser(User.builder().build())
                .setCertificates(new ArrayList<>())
                .build();

        Mockito.when(orderDao.findById(testData.getId())).thenReturn(Optional.of(testData));

        service.delete(1);

        Mockito.verify(orderDao).delete(testData);

    }
}