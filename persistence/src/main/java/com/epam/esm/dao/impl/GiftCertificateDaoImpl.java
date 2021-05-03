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
        Long count = 0L;
        CriteriaQuery<GiftCertificate> criteriaQuery = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = criteriaQuery.from(GiftCertificate.class);

        if (!params.isEmpty()) {
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
            criteriaQuery.select(certificateRoot).where(cb.and(predicates.toArray(Predicate[]::new)));
//            countQuery.select(cb.count(countQuery.from(GiftCertificate.class))).where(criteriaQuery.getRestriction());
            if (params.containsKey("sort_by")) {
                if (params.get("sort_by").split(":")[0].equals("asc")) {
                    criteriaQuery.orderBy(cb.asc(certificateRoot.get(params.get("sort_by").split(":")[1])));
                }
                if (params.get("sort_by").split(":")[0].equals("desc")) {
                    criteriaQuery.orderBy(cb.desc(certificateRoot.get(params.get("sort_by").split(":")[1])));
                }
            }
        }
        countQuery.select(cb.count(countQuery.from(GiftCertificate.class)));
        count = em.createQuery(countQuery).getSingleResult();
        int totalPages = getTotalPages(count.intValue(), pageSize);

        return new PageableResponse<>(em.createQuery(criteriaQuery).setFirstResult((page - 1) * pageSize).setMaxResults(pageSize).getResultList(), page, totalPages, pageSize, count.intValue());
    }

    public Optional<GiftCertificate> findByName(String name) {
        GiftCertificate result;
        try {
            result = em.createQuery("FROM GiftCertificate WHERE name ='" + name + "'", GiftCertificate.class).getSingleResult();
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
