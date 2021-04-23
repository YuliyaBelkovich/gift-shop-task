package com.epam.esm.dao;

import com.epam.esm.models.GiftCertificate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.sql.*;
import java.util.*;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private SessionFactory sessionFactory;
    private EntityManagerFactory factory;

    @Autowired
    public GiftCertificateDaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }


    @Override
    public List<GiftCertificate> findAll() {
        return factory.createEntityManager().createQuery("SELECT a FROM GiftCertificate a", GiftCertificate.class).getResultList();
    }

    @Override
    public Optional<GiftCertificate> findById(int id) {
        return Optional.of(factory.createEntityManager().find(GiftCertificate.class, id));
    }

    @Override
    @Transactional
    public GiftCertificate add(GiftCertificate certificate) {
        GiftCertificate result;
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        result = em.merge(certificate);
        em.getTransaction().commit();
        return result;
    }

    @Override
    @Transactional
    public void update(GiftCertificate certificate) {
        sessionFactory.openSession().getEntityManagerFactory().createEntityManager().merge(certificate);
    }

    @Override
    public void delete(int id) {
        sessionFactory.openSession().getEntityManagerFactory().createEntityManager().remove(findById(id));
    }
}
