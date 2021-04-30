package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.PageableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;

import java.util.*;

@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate> implements GiftCertificateDao {

    private EntityManager em;

    @Autowired
    public GiftCertificateDaoImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    protected Class<GiftCertificate> getIdentityClass() {
        return GiftCertificate.class;
    }

    public PageableResponse<GiftCertificate> findAll(Map<String, String> params, int page, int pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(GiftCertificate.class)));
        Long count = em.createQuery(countQuery).getSingleResult();

        CriteriaQuery<GiftCertificate> criteriaQuery = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(certificateRoot);

        int totalPages;
        if (count.intValue() % pageSize > 0) {
            totalPages = (count.intValue() / pageSize) + 1;
        } else {
            totalPages = count.intValue() / pageSize;
        }

        if (params.isEmpty()) {
            return ((page - 1) * 2 < count.intValue()) ?
                    new PageableResponse<>(em.createQuery(criteriaQuery).setFirstResult((page - 1) * pageSize).setMaxResults(pageSize).getResultList(), page, totalPages, pageSize)
                    : new PageableResponse<>(new ArrayList<>(), page, totalPages, pageSize);
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
        return ((page - 1) * 2 < count.intValue()) ?
                new PageableResponse<>(em.createQuery(criteriaQuery).setFirstResult((page - 1) * pageSize).setMaxResults(pageSize).getResultList(), page, totalPages, pageSize)
                : new PageableResponse<>(new ArrayList<>(), page, totalPages, pageSize);
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
    public void update(GiftCertificate certificate) {
        em.merge(certificate);
    }
}
