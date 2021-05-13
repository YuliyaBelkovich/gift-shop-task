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
                (Tag) em.createNativeQuery("select t.id, t.name from (select tag.id, tag.name, max(frequency) \n" +
                        "from (select count(tag.id) as frequency, tag.id, tag.name, order_id, top_1_user_id.user_id, order_certificate.gift_certificate_id \n" +
                        "from tag join gift_certificate_tag on tag.id = gift_certificate_tag.tag_id \n" +
                        "join gift_certificate on gift_certificate_tag.gift_certificate_id = gift_certificate.id\n" +
                        "inner join order_certificate on gift_certificate.id = order_certificate.gift_certificate_id \n" +
                        "inner join orders on order_certificate.order_id = orders.id \n" +
                        "inner join (select user_id\n" +
                        "                            from (\n" +
                        "                                     select distinct user_id,\n" +
                        "                                            sum_cost,\n" +
                        "                                            rank() over (order by sum_cost desc) as rank2\n" +
                        "                                     from (\n" +
                        "                                              select distinct orders.*,\n" +
                        "                                                     sum(orders.cost) over (partition by orders.user_id) as sum_cost\n" +
                        "                                              from orders\n" +
                        "                                          ) as sums_of_users\n" +
                        "                                 ) as ranked_sums\n" +
                        "                            where ranked_sums.rank2 = 1\n" +
                        "                        ) as top_1_user_id on top_1_user_id.user_id=orders.user_id group by tag.id) \n" +
                        "as tag) as t;", Tag.class).getSingleResult();
        return result == null ? Optional.empty() : Optional.of(result);
    }
}
