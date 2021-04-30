package com.epam.esm.service;

import com.epam.esm.dto.request.TagRequest;
import com.epam.esm.dto.response.TagResponse;
import com.epam.esm.models.PageableResponse;

import java.util.List;

public interface TagService {

    PageableResponse<TagResponse> findAll(int page, int pageSize);

    TagResponse findById(int id);

    TagResponse save(TagRequest tag);

    void delete(int id);

}
