package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.request.OrderRequest;
import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.exception.ExceptionDefinition;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Order;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private UserDao userDao;
    private GiftCertificateDao giftCertificateDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, GiftCertificateDao giftCertificateDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public PageableResponse<OrderResponse> findAll(int page, int pageSize) {
        PageableResponse<Order> orders = orderDao.findAll(page, pageSize);
        List<OrderResponse> responses = orders.getResponses().stream()
                .map(OrderResponse::toDto).collect(Collectors.toList());
        return new PageableResponse<>(responses,
                orders.getCurrentPage(),
                orders.getLastPage(),
                orders.getPageSize(),
                orders.getTotalElements());
    }

    @Override
    public OrderResponse findById(int id) {
        return OrderResponse.toDto(orderDao.findById(id)
                .orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
    }

    @Override
    public OrderResponse save(OrderRequest orderRequest) {
        Order order = orderRequest.toIdentity();
        order.setUser(userDao.findById(order.getUser().getId())
                .orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
        double cost = 0;
        List<GiftCertificate> giftCertificates = order.getCertificates().stream()
                .map(g -> giftCertificateDao.findById(g.getId())
                        .orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)))
                .collect(Collectors.toList());
        for (GiftCertificate g :
                giftCertificates) {
            cost += g.getPrice();
        }
        order.setCertificates(giftCertificates);
        order.setCost(cost);
        orderDao.add(order);
        return OrderResponse.toDto(orderDao.findById(order.getId())
                .orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
    }

    @Override
    public void delete(int id) {
        orderDao.delete(orderDao.findById(id).orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
    }
}
