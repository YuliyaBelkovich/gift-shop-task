package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Order;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private EntityManager em;

    @Autowired
    public UserDaoImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    @Override
    protected Class<User> getIdentityClass() {
        return User.class;
    }

    public PageableResponse<Order> findUserOrders(int id, int page, int pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<User> userCountRoot = countQuery.from(User.class);
        countQuery.select(userCountRoot.get("orders")).where(cb.equal(userCountRoot.get("id"), id));
        countQuery.select(cb.count(userCountRoot));
        Long count = em.createQuery(countQuery).getSingleResult();

        int totalPages = getTotalPages(count.intValue(), pageSize);

        CriteriaQuery<Order> criteriaQuery = cb.createQuery(Order.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot.get("orders")).where(cb.equal(userRoot.get("id"), id));

        return new PageableResponse<>(em.createQuery(criteriaQuery)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList(), page, totalPages, pageSize, count.intValue());
    }

    public Optional<Order> findUserOrderById(int userId, int orderId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = cb.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.where(cb.and(cb.equal(orderRoot.get("id"), orderId), cb.equal(orderRoot.join("user").get("id"), userId)));
        criteriaQuery.select(orderRoot);
        Order result;
        try {
            result = em.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.of(result);
    }
}
