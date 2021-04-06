package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagRequest;
import com.epam.esm.dto.TagResponse;
import com.epam.esm.dto.TagResponseContainer;
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
        return TagResponse.toDto(Objects.requireNonNull(tagDao.findById(id).orElse(null)));
    }

    public void save(TagRequest tag) {
        tagDao.add(TagRequest.toIdentity(tag));
    }

    public void delete(int id) {
        tagDao.delete(id);
    }
}
