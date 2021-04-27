package com.epam.esm.dao;

import com.epam.esm.models.Order;
import com.epam.esm.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<User> {

    List<Order> findUserOrders(int id);

    Optional<Order> findUserOrderById(int userId, int orderId);
}
