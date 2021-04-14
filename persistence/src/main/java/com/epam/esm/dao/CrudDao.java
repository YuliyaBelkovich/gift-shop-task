package com.epam.esm.dao;

import com.epam.esm.models.Identifiable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CrudDao<T extends Identifiable> {

    List<T> findAll();

    Optional<T> findById(int id);

    Optional<T> findByName(String name);

    void add(T identity);

    void update(T identity);

    void delete(int id);
}
