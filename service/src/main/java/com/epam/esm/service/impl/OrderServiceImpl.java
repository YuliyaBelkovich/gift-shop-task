package com.epam.esm.service.impl;

import com.epam.esm.dto.request.OrderRequest;
import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Override
    public List<OrderResponse> findAll() {
        return null;
    }

    @Override
    public OrderResponse findById(int id) {
        return null;
    }

    @Override
    public OrderResponse save(OrderRequest orderRequest) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
