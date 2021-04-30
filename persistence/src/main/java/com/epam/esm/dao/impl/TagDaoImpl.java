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
