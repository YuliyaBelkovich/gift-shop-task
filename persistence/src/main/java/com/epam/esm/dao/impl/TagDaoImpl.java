package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {

    private EntityManager em;

    @Autowired
    public TagDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Tag> findAll() {
        return em.createQuery("SELECT a FROM Tag a", Tag.class).getResultList();
    }

    public List<Tag> findAll(int page) {
        int pageSize = 2;
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(Tag.class)));
        Long count = em.createQuery(countQuery).getSingleResult();
        CriteriaQuery<Tag> tagCriteriaQuery = cb.createQuery(Tag.class);
        Root<Tag> tagRoot = tagCriteriaQuery.from(Tag.class);
        CriteriaQuery<Tag> select = tagCriteriaQuery.select(tagRoot);

        return ((page - 1) * 2 < count.intValue()) ?
                em.createQuery(select).setFirstResult((page - 1) * 2).setMaxResults(pageSize).getResultList()
                : em.createQuery(select).setFirstResult(0).setMaxResults(pageSize).getResultList();
    }

    @Override
    public Optional<Tag> findById(int id) {
        Tag result = em.find(Tag.class, id);
        return result == null ? Optional.empty() : Optional.of(result);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Tag result;
        try {
            result = em.createQuery("FROM Tag WHERE name = '" + name + "'", Tag.class).getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.of(result);
    }

    @Override
    public void add(Tag tag) {
        em.persist(tag);
    }

    @Override
    public void delete(Tag tag) {
        em.remove(tag);
    }

//    public Tag getMostWidelyUsedTag(){
//       return em.createQuery("select tag.id, tag.name\n" +
//                "    from (select tag.id, count(tag.id) as count, tag.name from tag join gift_certificate on tag.id=gift_certificate.id\n" +
//                "        join orders on orders.id=gift_certificate.id\n" +
//                "        join (select user_id, max(sum) as max from\n" +
//                "                    (select user_id, sum(orders.cost) as sum\n" +
//                "                    from user_gift join orders on user_gift.id=orders.user_id) as b) as t on orders.user_id = t.user_id) as tag group by count", Tag.class).getSingleResult();
//
//    }
}
