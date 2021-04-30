package com.epam.esm.dao;

import com.epam.esm.models.Order;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<User> {

    PageableResponse<Order> findUserOrders(int id, int page, int pageSize);

    Optional<Order> findUserOrderById(int userId, int orderId);
}
