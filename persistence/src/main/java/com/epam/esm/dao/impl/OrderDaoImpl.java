package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {

    private EntityManager em;

    @Autowired
    public OrderDaoImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    @Override
    protected Class<Order> getIdentityClass() {
        return Order.class;
    }
}
