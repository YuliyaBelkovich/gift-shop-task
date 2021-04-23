package com.epam.esm.dao;

import static org.hamcrest.junit.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import com.epam.esm.dao.config.PersistenceTestConfig;
import com.epam.esm.models.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceTestConfig.class)
class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDao dao;

    @Test
    void findById() {
        GiftCertificate testData = GiftCertificate.builder().setId(1)
                .setName("test 1")
                .setDescription("description 1")
                .setPrice(1.0)
                .setDuration(1)
                .setCreateDate(LocalDateTime.of(2021, 1, 1, 0, 0, 1))
                .setLastUpdateDate(LocalDateTime.of(2021, 1, 1, 0, 0, 1))
                .build();

        GiftCertificate actual = dao.findById(1).get();

        assertThat(testData, is(actual));
    }

//
//    @Test
//    void findWithParams() {
//        Map<String, String> params = new HashMap<>();
//        params.put("tag_name", "test tag 1");
//        params.put("name", "test");
//        params.put("sort_by", "name");
//        params.put("order", "DESC");
//
//        GiftCertificate gc1 = GiftCertificate.builder().setId(1)
//                .setName("test 1")
//                .setDescription("description 1")
//                .setPrice(1.0)
//                .setDuration(1)
//                .setCreateDate(LocalDateTime.of(2021, 1, 1, 0, 0, 1))
//                .setLastUpdateDate(LocalDateTime.of(2021, 1, 1, 0, 0, 1))
//                .build();
//
//        GiftCertificate gc2 = GiftCertificate.builder().setId(3)
//                .setName("test 3")
//                .setDescription("description 3")
//                .setPrice(3.0)
//                .setDuration(3)
//                .setCreateDate(LocalDateTime.of(2021, 1, 1, 0, 0, 1))
//                .setLastUpdateDate(LocalDateTime.of(2021, 1, 1, 0, 0, 1))
//                .build();
//
//        List<GiftCertificate> actual = dao.findWithParams(params);
//
//        assertThat(2, is(equalTo(actual.size())));
//        assertThat(gc2, is(actual.get(0)));
//        assertThat(gc1, is(actual.get(1)));
//    }


    @Test
    void add() {
        GiftCertificate testData = GiftCertificate.builder().setId(6)
                .setName("test 6")
                .setDescription("description 6")
                .setPrice(6.0)
                .setDuration(6)
                .build();

        dao.add(testData);

        GiftCertificate actual = dao.findById(6).get();

        assertNotNull(actual);

        assertEquals(testData.getId(), actual.getId());
        assertEquals(testData.getName(), actual.getName());
        assertEquals(testData.getDescription(), actual.getDescription());
        assertEquals(testData.getPrice(), actual.getPrice());
        assertEquals(testData.getDuration(), actual.getDuration());
    }

    @Test
    void update() {
        GiftCertificate testData = GiftCertificate.builder().setId(5)
                .setName("test 7")
                .setPrice(7.0)
                .build();

        dao.update(testData);

        GiftCertificate actual = dao.findById(5).get();

        assertNotNull(actual);

        assertEquals(testData.getId(), actual.getId());
        assertEquals(testData.getName(), actual.getName());
        assertEquals(testData.getPrice(), actual.getPrice());
    }

//    @Test
//    void delete() {
//        dao.delete(4);
//
//        GiftCertificate actual = dao.findById(4).orElse(null);
//
//        assertNull(actual);
//    }
}