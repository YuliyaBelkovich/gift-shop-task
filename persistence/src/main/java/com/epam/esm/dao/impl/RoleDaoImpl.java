package com.epam.esm.dao.impl;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {

    private EntityManager em;

    @Autowired
    public RoleDaoImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    @Override
    protected Class<Role> getIdentityClass() {
        return Role.class;
    }

    @Override
    public Optional<Role> findByName(String name) {
        Role result;
        try {
            result = em.createQuery("FROM Role WHERE name ='" + name + "'", Role.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.of(result);
    }
}
