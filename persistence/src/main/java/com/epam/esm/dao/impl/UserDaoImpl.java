package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Order;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
        countQuery.select(cb.count(countQuery.from(GiftCertificate.class)));
        Long count = em.createQuery(countQuery).getSingleResult();

        int totalPages;
        if (count.intValue() % pageSize > 0) {
            totalPages = (count.intValue() / pageSize) + 1;
        } else {
            totalPages = count.intValue() / pageSize;
        }

        CriteriaQuery<Order> criteriaQuery = cb.createQuery(Order.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot.get("orders")).where(cb.equal(userRoot.get("id"), id));

        return ((page - 1) * 2 < count.intValue()) ?
                new PageableResponse<>(em.createQuery(criteriaQuery).setFirstResult((page - 1) * pageSize).setMaxResults(pageSize).getResultList(), page, totalPages, pageSize, count.intValue())
                : new PageableResponse<>(new ArrayList<>(), page, totalPages, pageSize, count.intValue());
    }

    public Optional<Order> findUserOrderById(int userId, int orderId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = cb.createQuery(Order.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot.get("orders")).where(cb.and(cb.equal(userRoot.get("id"), userId), cb.equal(userRoot.join("orders").get("id"), orderId)));
        return Optional.of(em.createQuery(criteriaQuery).getSingleResult());
    }
}
