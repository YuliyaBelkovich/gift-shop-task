package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDaoImpl;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateRequest;
import com.epam.esm.dto.GiftCertificateResponse;
import com.epam.esm.dto.GiftCertificateResponseContainer;
import com.epam.esm.models.GiftCertificate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class GiftCertificateServiceImplTest {

    @InjectMocks
    @Autowired
    GiftCertificateServiceImpl service;

    @Mock
    GiftCertificateDaoImpl dao;

    @Mock
    TagDao tagDao;


    @Test
    void findById() {
        GiftCertificate testData = GiftCertificate.builder().setId(1)
                .setName("test 4")
                .setDescription("description 4")
                .setPrice(4.0)
                .setDuration(4)
                .setCreateDate(LocalDateTime.now())
                .setLastUpdateDate(LocalDateTime.now())
                .build();

        GiftCertificateResponse expected = GiftCertificateResponse.toDto(testData, new ArrayList<>());

        Mockito.when(dao.findById(1)).thenReturn(Optional.of(testData));
        Mockito.when(tagDao.findByGiftId(1)).thenReturn(new ArrayList<>());

        GiftCertificateResponse actual = service.findById(1);

        assertEquals(expected, actual);

    }

    @Test
    void findAll() {
        GiftCertificate testData = GiftCertificate.builder().setId(1)
                .setName("test 4")
                .setDescription("description 4")
                .setPrice(4.0)
                .setDuration(4)
                .setCreateDate(LocalDateTime.now())
                .setLastUpdateDate(LocalDateTime.now())
                .build();
        List<GiftCertificate> testDataList = new ArrayList<>();
        testDataList.add(testData);

        List<GiftCertificateResponse> testList = new ArrayList<>();
        testList.add(GiftCertificateResponse.toDto(testData, new ArrayList<>()));

        GiftCertificateResponseContainer expected = new GiftCertificateResponseContainer(testList);

        Mockito.when(dao.findAll()).thenReturn(testDataList);
        Mockito.when(tagDao.findByGiftId(1)).thenReturn(new ArrayList<>());

        GiftCertificateResponseContainer actual = service.findAll();

        assertEquals(expected, actual);

    }

    @Test
    void save() {
        GiftCertificate testData = GiftCertificate.builder().setId(0)
                .setName("test 4")
                .setDescription("description 4")
                .setPrice(4.0)
                .setDuration(4)
                .build();

        service.save(GiftCertificateRequest.toDto(testData, new ArrayList<>()));

        Mockito.verify(dao).add(testData, new ArrayList<>());

    }

    @Test
    void update() {
        GiftCertificate testData = GiftCertificate.builder().setId(1)
                .setName("test 4")
                .setDescription("description 4")
                .setPrice(4.0)
                .setDuration(4)
                .build();

        service.update(GiftCertificateRequest.toDto(testData, new ArrayList<>()), 1);

        Mockito.verify(dao).update(testData, new ArrayList<>());
    }

    @Test
    void delete() {

        service.delete(1);

        Mockito.verify(dao).delete(1);

    }
}