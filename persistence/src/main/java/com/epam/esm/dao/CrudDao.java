package com.epam.esm.dao;

import com.epam.esm.models.Identifiable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T extends Identifiable> {

    List<T> findAll();

    Optional<T> findById(int id);

    void add(T identity);

    void delete(T identity);
}
