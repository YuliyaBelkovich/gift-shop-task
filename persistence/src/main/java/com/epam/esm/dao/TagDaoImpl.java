package com.epam.esm.dao;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {

    private SessionFactory sessionFactory;
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public TagDaoImpl(EntityManagerFactory factory) {
        this.entityManagerFactory = factory;
    }

    @Override
    public List<Tag> findAll() {
        return sessionFactory.openSession().createQuery("SELECT a FROM Tag a", Tag.class).list();
    }

    @Override
    public Optional<Tag> findById(int id) {
        return Optional.of(sessionFactory.openSession().get(Tag.class, id));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return Optional.of(em.createQuery("FROM Tag WHERE name = '" + name + "'", Tag.class).getSingleResult());
    }

    @Override
    public Tag add(Tag tag) {
        sessionFactory.openSession().save(tag);
        return findById(tag.getId()).get();
    }

    @Override
    public void update(Tag identity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Tag> findByGiftId(int id) {
        return null;
    }
}
