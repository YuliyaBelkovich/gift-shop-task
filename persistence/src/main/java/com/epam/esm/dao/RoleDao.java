package com.epam.esm.dao;

import com.epam.esm.models.Role;

import java.util.Optional;

public interface RoleDao {

    Optional<Role> findByName(String name);
}
