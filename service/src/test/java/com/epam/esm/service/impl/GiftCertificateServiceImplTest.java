package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.request.GiftCertificateRequest;
import com.epam.esm.dto.request.GiftCertificateUpdateRequest;
import com.epam.esm.dto.response.GiftCertificateResponse;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateServiceImplTest {

    @InjectMocks
    GiftCertificateServiceImpl service;

    @Mock
    GiftCertificateDaoImpl dao;

    @Mock
    TagDao tagDao;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        GiftCertificate testData = GiftCertificate.builder().setId(4)
                .setName("test 4")
                .setDescription("description 4")
                .setPrice(4.0)
                .setDuration(4)
                .setCreateDate(LocalDateTime.now())
                .setLastUpdateDate(LocalDateTime.now())
                .setTags(new HashSet<>())
                .build();
        List<GiftCertificate> testDataList = new ArrayList<>();
        testDataList.add(testData);

        List<GiftCertificateResponse> expected = new ArrayList<>();
        expected.add(GiftCertificateResponse.toDto(testData));

        Mockito.when(dao.findAll(new HashMap<>(), 1, 1)).thenReturn(new PageableResponse<GiftCertificate>(testDataList, 1, 1, 1, 1));

        List<GiftCertificateResponse> actual = service.findAll(new HashMap<>(), 1, 1).getResponses();

        assertEquals(expected, actual);

    }

    @Test
    void findById() {
        GiftCertificate testData = GiftCertificate.builder().setId(1)
                .setName("test 4")
                .setDescription("description 4")
                .setPrice(4.0)
                .setDuration(4)
                .setCreateDate(LocalDateTime.now())
                .setLastUpdateDate(LocalDateTime.now())
                .setTags(new HashSet<>())
                .build();

        GiftCertificateResponse expected = GiftCertificateResponse.toDto(testData);

        Mockito.when(dao.findById(1)).thenReturn(Optional.of(testData));

        GiftCertificateResponse actual = service.findById(1);

        assertEquals(expected, actual);

    }

    @Test
    void save() {
        GiftCertificate testData = GiftCertificate.builder().setId(0)
                .setName("test 4")
                .setDescription("description 4")
                .setPrice(4.0)
                .setDuration(4)
                .setTags(new HashSet<>())
                .build();

        Mockito.when(dao.findById(testData.getId())).thenReturn(Optional.of(testData));

        service.save(GiftCertificateRequest.toDto(testData, new HashSet<>()));

        Mockito.verify(dao).add(testData);

    }

    @Test
    void update() {
        GiftCertificate testData = GiftCertificate.builder().setId(1)
                .setName("test 4")
                .setDescription("description 4")
                .setPrice(4.0)
                .setTags(new HashSet<>())
                .build();

        Mockito.when(dao.findById(testData.getId())).thenReturn(Optional.of(testData));

        service.update(GiftCertificateUpdateRequest.toDto(testData, new ArrayList<>()), 1);

        Mockito.verify(dao).update(testData);
    }

    @Test
    void delete() {
        GiftCertificate testData = GiftCertificate.builder().setId(1)
                .setName("test 4")
                .setDescription("description 4")
                .setPrice(4.0)
                .setTags(new HashSet<>())
                .build();

        Mockito.when(dao.findById(testData.getId())).thenReturn(Optional.of(testData));

        service.delete(1);

        Mockito.verify(dao).delete(testData);

    }
}