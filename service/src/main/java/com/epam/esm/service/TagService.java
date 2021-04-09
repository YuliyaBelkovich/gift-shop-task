package com.epam.esm.service;

import com.epam.esm.dto.TagRequest;
import com.epam.esm.dto.TagResponse;

import java.util.List;

public interface TagService {

    List<TagResponse> findAll();

    TagResponse findById(int id);

    TagResponse save(TagRequest tag);

    void delete(int id);

}
