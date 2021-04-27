package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    private EntityManager em;

    @Autowired
    public OrderDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Order> findAll() {
        return em.createQuery("SELECT a FROM Order a", Order.class).getResultList();
    }

    @Override
    public Optional<Order> findById(int id) {
        Order result = em.find(Order.class, id);
        return result == null ? Optional.empty() : Optional.of(result);
    }

    public List<Order> findByUserId(int userId) {
        return em.createQuery("from Order WHERE User.id='" + userId+"'", Order.class).getResultList();
    }

    @Override
    public void add(Order order) {
        em.persist(order);
    }

    @Override
    public void delete(Order order) {
        em.remove(order);
    }

}
