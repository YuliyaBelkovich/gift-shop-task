package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.models.Order;
import com.epam.esm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private EntityManager em;

    @Autowired
    public UserDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT a FROM User a", User.class).getResultList();
    }

    @Override
    public Optional<User> findById(int id) {
        User result = em.find(User.class, id);
        return result == null ? Optional.empty() : Optional.of(result);
    }

    public List<Order> findUserOrders(int id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = cb.createQuery(Order.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot.get("orders"));
        criteriaQuery.where(cb.equal(userRoot.get("id"), id));
        return em.createQuery(criteriaQuery).getResultList();
    }

    public Optional<Order> findUserOrderById(int userId, int orderId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = cb.createQuery(Order.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot.get("orders"));
        criteriaQuery.where(cb.and(cb.equal(userRoot.get("id"), userId), cb.equal(userRoot.join("orders").get("id"),orderId)));
        return Optional.of(em.createQuery(criteriaQuery).getSingleResult());
    }

    @Override
    public void add(User user) {

    }

    @Override
    public void delete(User user) {

    }
}
