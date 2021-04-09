package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagRequest;
import com.epam.esm.dto.TagResponse;
import com.epam.esm.dto.TagResponseContainer;
import com.epam.esm.exception.ExceptionManager;
import com.epam.esm.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Validated
public class TagServiceImpl implements TagService {
    private TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public TagResponseContainer findAll() {
        return new TagResponseContainer(tagDao.findAll().stream().map(TagResponse::toDto).collect(Collectors.toList()));
    }

    public TagResponse findById(int id) {
        return TagResponse.toDto(Objects.requireNonNull(tagDao.findById(id).orElseThrow(() -> new ServiceException(ExceptionManager.IDENTITY_NOT_FOUND))));
    }

    public void save(@Valid TagRequest tag) {
        if (tagDao.findByName(tag.getName()).isEmpty()) {
            tagDao.add(TagRequest.toIdentity(tag));
        } else {
            throw new ServiceException(ExceptionManager.IDENTITY_ALREADY_EXISTS);
        }
    }

    public void delete(int id) {
        tagDao.delete(id);
    }
}
