package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.models.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;

import java.util.*;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private EntityManager em;

    @Autowired
    public GiftCertificateDaoImpl(EntityManager em) {
        this.em = em;
    }

    public List<GiftCertificate> findAll(Map<String, String> params) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(certificateRoot);

        if (params.isEmpty()) {
            return em.createQuery(criteriaQuery).getResultList();
        }

        List<Predicate> predicates = new ArrayList<>();
        if (params.containsKey("tag_names")) {
            List<Predicate> tagPredicates = new ArrayList<>();
            Arrays.stream(params.get("tag_names").split(";")).forEach(tagName -> tagPredicates.add(cb.equal(certificateRoot.join("tags").get("name"), tagName)));
            predicates.add(cb.and(tagPredicates.toArray(Predicate[]::new)));
        }
        if (params.containsKey("name")) {
            predicates.add(cb.like(certificateRoot.get("name"), "%" + params.get("name") + "%"));
        }
        if (params.containsKey("description")) {
            predicates.add(cb.like(certificateRoot.get("description"), "%" + params.get("description") + "%"));
        }
        criteriaQuery.where(predicates.toArray(Predicate[]::new));
        if (params.containsKey("sort_by")) {
            if (String.valueOf(params.get("sort_by").charAt(0)).equals("+")) {
                criteriaQuery.orderBy(cb.asc(certificateRoot.get(params.get("sort_by"))));
            } else {
                criteriaQuery.orderBy(cb.desc(certificateRoot.get(params.get("sort_by"))));
            }
        }
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<GiftCertificate> findAll() {
        return em.createQuery("SELECT a FROM GiftCertificate a", GiftCertificate.class).getResultList();
    }

    @Override
    public Optional<GiftCertificate> findById(int id) {
        GiftCertificate result = em.find(GiftCertificate.class, id);
        return result == null ? Optional.empty() : Optional.of(result);
    }

    public Optional<GiftCertificate> findByName(String name) {
        GiftCertificate result;
        try {
            result = em.createQuery("FROM GiftCetificate WHERE name = '" + name + "'", GiftCertificate.class).getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.of(result);
    }

    @Override
    public void add(GiftCertificate certificate) {
        em.persist(certificate);
    }

    @Override
    public void update(GiftCertificate certificate) {
        em.merge(certificate);
    }

    @Override
    public void delete(GiftCertificate certificate) {
        em.remove(certificate);
    }
}
