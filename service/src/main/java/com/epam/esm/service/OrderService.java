package com.epam.esm.service;

import com.epam.esm.dto.request.OrderRequest;
import com.epam.esm.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> findAll();

    OrderResponse findById(int id);

    OrderResponse save(OrderRequest orderRequest);

    void delete(int id);

}
