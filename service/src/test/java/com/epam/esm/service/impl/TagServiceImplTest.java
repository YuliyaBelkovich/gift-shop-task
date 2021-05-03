package com.epam.esm.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.request.TagRequest;
import com.epam.esm.dto.response.TagResponse;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.models.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class TagServiceImplTest {

    @InjectMocks
    TagServiceImpl service;

    @Mock
    TagDao tagDao;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Tag firstTag = Tag.builder().setId(1).setName("test tag 1").build();
        List<Tag> tagList = new ArrayList<>();
        tagList.add(firstTag);

        List<TagResponse> expected = new ArrayList<>();
        expected.add(TagResponse.toDto(firstTag));

        Mockito.when(tagDao.findAll(1, 1)).thenReturn(new PageableResponse<Tag>(tagList, 1, 1, 1, 1));

        List<TagResponse> actual = service.findAll(1, 1).getResponses();

        assertEquals(expected, actual);
    }

    @Test
    void findById() {
        Tag testData = Tag.builder().setId(1).setName("test tag 1").build();
        TagResponse expected = TagResponse.toDto(testData);

        Mockito.when(tagDao.findById(1)).thenReturn(Optional.of(testData));

        TagResponse actual = service.findById(1);

        assertEquals(expected, actual);
    }

    @Test
    void save() {
        Tag testData = Tag.builder().setId(0).setName("test tag 4").build();

        Mockito.when(tagDao.findById(testData.getId())).thenReturn(Optional.of(testData));

        service.save(TagRequest.toDto(testData));

        Mockito.verify(tagDao).add(testData);
    }

    @Test
    void delete() {
        Tag testData = Tag.builder().setId(1).setName("test tag 4").build();

        Mockito.when(tagDao.findById(testData.getId())).thenReturn(Optional.of(testData));

        service.delete(1);

        Mockito.verify(tagDao).delete(testData);

    }
}