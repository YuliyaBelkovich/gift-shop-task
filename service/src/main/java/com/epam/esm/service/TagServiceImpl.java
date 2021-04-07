package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagRequest;
import com.epam.esm.dto.TagResponse;
import com.epam.esm.dto.TagResponseContainer;
import com.epam.esm.exception.IdentityAlreadyExistsException;
import com.epam.esm.exception.IdentityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
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
        return TagResponse.toDto(Objects.requireNonNull(tagDao.findById(id).orElseThrow(() -> new IdentityNotFoundException(id))));
    }

    public void save(TagRequest tag) {
        if (tagDao.findByName(tag.getName()).isEmpty()) {
            tagDao.add(TagRequest.toIdentity(tag));
        } else {
            throw new IdentityAlreadyExistsException(tag.toString());
        }
    }

    public void delete(int id) {
        tagDao.delete(id);
    }
}
