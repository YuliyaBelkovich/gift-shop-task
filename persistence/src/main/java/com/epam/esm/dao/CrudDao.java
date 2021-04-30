package com.epam.esm.dao;

import com.epam.esm.models.Identifiable;
import com.epam.esm.models.PageableResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T extends Identifiable> {

    PageableResponse<T> findAll(int page, int pageSize);

    Optional<T> findById(int id);

    void add(T identity);

    void delete(T identity);
}
