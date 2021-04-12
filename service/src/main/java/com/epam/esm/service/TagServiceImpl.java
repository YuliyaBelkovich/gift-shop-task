package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagRequest;
import com.epam.esm.dto.TagResponse;
import com.epam.esm.exception.ExceptionDefinition;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public List<TagResponse> findAll() {
        return tagDao.findAll().stream().map(TagResponse::toDto).collect(Collectors.toList());
    }

    public TagResponse findById(int id) {
        return TagResponse.toDto(Objects.requireNonNull(tagDao.findById(id)
                .orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND))));
    }

    public TagResponse save(TagRequest tag) {
        Tag tagToSave = TagRequest.toIdentity(tag);
        if (tagDao.findByName(tag.getName()).isEmpty()) {
            tagDao.add(tagToSave);
        } else {
            throw new ServiceException(ExceptionDefinition.IDENTITY_ALREADY_EXISTS);
        }
        return findById(tagToSave.getId());
    }

    public void delete(int id) {
        tagDao.delete(id);
    }
}
