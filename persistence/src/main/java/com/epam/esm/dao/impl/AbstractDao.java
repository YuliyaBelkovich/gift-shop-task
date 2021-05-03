package com.epam.esm.dao.impl;

import com.epam.esm.dao.CrudDao;
import com.epam.esm.models.Identifiable;
import com.epam.esm.models.PageableResponse;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Optional;

public abstract class AbstractDao<T extends Identifiable> implements CrudDao<T> {

    private EntityManager em;

    public AbstractDao(EntityManager em) {
        this.em = em;
    }

    protected abstract Class<T> getIdentityClass();

    public PageableResponse<T> findAll(int page, int pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(getIdentityClass())));
        Long count = em.createQuery(countQuery).getSingleResult();
        CriteriaQuery<T> tagCriteriaQuery = cb.createQuery(getIdentityClass());
        Root<T> root = tagCriteriaQuery.from(getIdentityClass());
        CriteriaQuery<T> select = tagCriteriaQuery.select(root);

        int totalPages = getTotalPages(count.intValue(), pageSize);
        return
                new PageableResponse<>(em.createQuery(select)
                        .setFirstResult((page - 1) * pageSize)
                        .setMaxResults(pageSize).getResultList(),
                        page, totalPages, pageSize, count.intValue());
    }

    @Override
    public Optional<T> findById(int id) {
        T result = em.find(getIdentityClass(), id);
        return result == null ? Optional.empty() : Optional.of(result);
    }

    protected int getTotalPages(int count, int pageSize) {
        return (count % pageSize > 0) ? (count / pageSize) + 1 : count / pageSize;
    }

    @Override
    public void add(T identity) {
        em.persist(identity);
    }

    @Override
    public void delete(T identity) {
        em.remove(identity);
    }
}
