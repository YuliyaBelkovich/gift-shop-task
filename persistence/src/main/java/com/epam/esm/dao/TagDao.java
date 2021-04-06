package com.epam.esm.dao;

import com.epam.esm.models.Identifiable;
import com.epam.esm.models.Tag;

import java.util.List;

public interface TagDao extends CrudDao<Tag> {
    List<Tag> findByGiftId(int id);

    void add(Tag identity, List<? extends Identifiable> list);

    void update(Tag identity, List<? extends Identifiable> list);
}
