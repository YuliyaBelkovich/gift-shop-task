package com.epam.esm.service;

import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.TagDao;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;

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

//    @Test
//    void findAll() {
//        GiftCertificate testData = GiftCertificate.builder().setId(1)
//                .setName("test 4")
//                .setDescription("description 4")
//                .setPrice(4.0)
//                .setDuration(4)
//                .setCreateDate(LocalDateTime.now())
//                .setLastUpdateDate(LocalDateTime.now())
//                .build();
//        List<GiftCertificate> testDataList = new ArrayList<>();
//        testDataList.add(testData);
//
//        List<GiftCertificateResponse> expected = new ArrayList<>();
//        expected.add(GiftCertificateResponse.toDto(testData, new ArrayList<>()));
//
//        Mockito.when(dao.findAll()).thenReturn(testDataList);
//        Mockito.when(tagDao.findByGiftId(1)).thenReturn(new ArrayList<>());
//
//        List<GiftCertificateResponse> actual = service.findAll(new HashMap<>());
//
//        assertEquals(expected, actual);
//
//    }

//    @Test
//    void findById() {
//        GiftCertificate testData = GiftCertificate.builder().setId(1)
//                .setName("test 4")
//                .setDescription("description 4")
//                .setPrice(4.0)
//                .setDuration(4)
//                .setCreateDate(LocalDateTime.now())
//                .setLastUpdateDate(LocalDateTime.now())
//                .build();
//
//        GiftCertificateResponse expected = GiftCertificateResponse.toDto(testData, new ArrayList<>());
//
//        Mockito.when(dao.findById(1)).thenReturn(Optional.of(testData));
//        Mockito.when(tagDao.findByGiftId(1)).thenReturn(new ArrayList<>());
//
//        GiftCertificateResponse actual = service.findById(1);
//
//        assertEquals(expected, actual);
//
//    }

//    @Test
//    void save() {
//        GiftCertificate testData = GiftCertificate.builder().setId(0)
//                .setName("test 4")
//                .setDescription("description 4")
//                .setPrice(4.0)
//                .setDuration(4)
//                .build();
//
//        Mockito.when(dao.findById(testData.getId())).thenReturn(Optional.of(testData));
//
//        service.save(GiftCertificateRequest.toDto(testData, new ArrayList<>()));
//
//        Mockito.verify(dao).add(testData, new ArrayList<>());
//
//    }

//    @Test
//    void update() {
//        GiftCertificate testData = GiftCertificate.builder().setId(1)
//                .setName("test 4")
//                .setDescription("description 4")
//                .setPrice(4.0)
//                .setDuration(4)
//                .build();
//
//        service.update(GiftCertificateUpdateRequest.toDto(testData, new ArrayList<>()), 1);
//
//        Mockito.verify(dao).update(testData, new ArrayList<>());
//    }

//    @Test
//    void delete() {
//
//        service.delete(2);
//
//        Mockito.verify(dao).delete(2);
//
//    }
}