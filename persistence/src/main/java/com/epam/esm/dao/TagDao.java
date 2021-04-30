package com.epam.esm.dao;

import com.epam.esm.models.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends CrudDao<Tag> {

    Optional<Tag> findByName(String name);
}
