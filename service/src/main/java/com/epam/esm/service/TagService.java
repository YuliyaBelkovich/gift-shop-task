package com.epam.esm.service;

import com.epam.esm.dto.TagRequest;
import com.epam.esm.dto.TagResponse;
import com.epam.esm.dto.TagResponseContainer;

public interface TagService {

    TagResponseContainer findAll();

    TagResponse findById(int id);

    void save(TagRequest tag);

    void delete(int id);

}
