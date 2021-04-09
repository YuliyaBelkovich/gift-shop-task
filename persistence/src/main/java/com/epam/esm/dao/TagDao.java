package com.epam.esm.dao;

import com.epam.esm.models.Tag;

import java.util.List;

public interface TagDao extends CrudDao<Tag> {
    List<Tag> findByGiftId(int id);
}
