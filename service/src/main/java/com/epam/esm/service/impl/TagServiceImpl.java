package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.request.TagRequest;
import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.dto.response.TagResponse;
import com.epam.esm.exception.ExceptionDefinition;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.models.Order;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.models.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public PageableResponse<TagResponse> findAll(int page, int pageSize) {
        PageableResponse<Tag> tags = tagDao.findAll(page, pageSize);
        List<TagResponse> responses = tags.getResponses().stream().map(TagResponse::toDto).collect(Collectors.toList());
        return new PageableResponse<>(responses, tags.getCurrentPage(), tags.getLastPage(), tags.getPageSize());
    }

    public TagResponse findById(int id) {
        return TagResponse.toDto(tagDao.findById(id).orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
    }

    public TagResponse save(TagRequest tag) {
        Tag tagToSave = TagRequest.toIdentity(tag);

        tagDao.findByName(tag.getName()).ifPresent(t -> {
            throw new ServiceException(ExceptionDefinition.IDENTITY_ALREADY_EXISTS);
        });
        tagDao.add(tagToSave);
        return findById(tagToSave.getId());
    }

    public void delete(int id) {
        tagDao.delete(tagDao.findById(id).orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
    }
}
