package com.epam.esm.service;

import com.epam.esm.dto.request.TagRequest;
import com.epam.esm.dto.response.TagResponse;

import java.util.List;

public interface TagService {

    List<TagResponse> findAll();

    TagResponse findById(int id);

    TagResponse save(TagRequest tag);

    void delete(int id);

    List<TagResponse> findAll(int page);

}
