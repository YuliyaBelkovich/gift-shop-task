package com.epam.esm.service;

import com.epam.esm.dto.request.TagRequest;
import com.epam.esm.dto.response.TagResponse;
import com.epam.esm.models.PageableResponse;

public interface TagService {

    PageableResponse<TagResponse> findAll(int page, int pageSize);

    TagResponse findById(int id);

    TagResponse save(TagRequest tag);

    TagResponse getMostWidelyUsedTag();

    void delete(int id);

}
