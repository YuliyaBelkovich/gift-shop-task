package com.epam.esm.service;

import com.epam.esm.dto.request.OrderRequest;
import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.models.PageableResponse;

public interface OrderService {

    PageableResponse<OrderResponse> findAll(int page, int pageSize);

    OrderResponse findById(int id);

    OrderResponse save(OrderRequest orderRequest);

    void delete(int id);

}
