package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {

    private EntityManager em;

    @Autowired
    public TagDaoImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    @Override
    protected Class<Tag> getIdentityClass() {
        return Tag.class;
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

    public Optional<Tag> getMostWidelyUsedTag() {
        Tag result =
                (Tag) em.createNativeQuery("select t.id, t.name from " +
                        "(select tag.id, tag.name, max(frequency) " +
                        "from (select count(tag.id) as frequency, tag.id, tag.name, order_id, " +
                        "user_max.user_id, order_certificate.gift_certificate_id " +
                        "from tag join gift_certificate_tag on tag.id = gift_certificate_tag.tag_id " +
                        "join gift_certificate on gift_certificate_tag.gift_certificate_id = gift_certificate.id " +
                        "inner join order_certificate on gift_certificate.id = order_certificate.gift_certificate_id " +
                        "inner join orders on order_certificate.order_id = orders.id " +
                        "inner join (select user_id, max(sum) " +
                        "from (select user_id, sum(cost) as sum from orders group by user_id) as user_cost) " +
                        "as user_max on user_max.user_id=orders.user_id group by tag.id) " +
                        "as tag) " +
                        "as t", Tag.class).getSingleResult();
        return result == null ? Optional.empty() : Optional.of(result);
    }
}
