package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDaoImpl;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagRequest;
import com.epam.esm.dto.TagResponse;
import com.epam.esm.dto.TagResponseContainer;
import com.epam.esm.models.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TagServiceImplTest {

    @InjectMocks
    @Autowired
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
       List<Tag> tagList =  new ArrayList<>();
       tagList.add(firstTag);

       List<TagResponse> responseList = new ArrayList<>();
       responseList.add(TagResponse.toDto(firstTag));

        TagResponseContainer expected = new TagResponseContainer(responseList);

        Mockito.when(tagDao.findAll()).thenReturn(tagList);

        TagResponseContainer actual = service.findAll();

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

        service.save(TagRequest.toDto(testData));

        Mockito.verify(tagDao).add(testData);
    }

    @Test
    void delete() {
        service.delete(1);

        Mockito.verify(tagDao).delete(1);

    }
}