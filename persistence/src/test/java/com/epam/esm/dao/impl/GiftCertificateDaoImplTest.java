package com.epam.esm.dao.impl;

import static org.hamcrest.junit.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.config.PersistenceTestConfig;
import com.epam.esm.models.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(classes = PersistenceTestConfig.class)
class GiftCertificateDaoImplTest {

    private GiftCertificateDao dao;

    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDao dao) {
        this.dao = dao;
    }

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


    @Test
    void findWithParams() {
        Map<String, String> params = new HashMap<>();
        params.put("tag_names", "test tag 1;test tag 3");
        params.put("name", "tes");
        params.put("sort_by", "asc:name");

        GiftCertificate gc1 = GiftCertificate.builder().setId(1)
                .setName("test 1")
                .setDescription("description 1")
                .setPrice(1.0)
                .setDuration(1)
                .setCreateDate(LocalDateTime.of(2021, 1, 1, 0, 0, 1))
                .setLastUpdateDate(LocalDateTime.of(2021, 1, 1, 0, 0, 1))
                .build();

        List<GiftCertificate> actual = dao.findAll(params, 1, 2).getResponses();

        assertThat(1, is(equalTo(actual.size())));
        assertThat(gc1, is(actual.get(0)));
    }


    @Test
    @Rollback
    void add() {
        GiftCertificate testData = GiftCertificate.builder()
                .setName("test 7")
                .setDescription("description 7")
                .setPrice(6.0)
                .setDuration(6)
                .build();

        dao.add(testData);

        GiftCertificate actual = dao.findById(7).get();

        assertEquals(testData, actual);
    }

    @Test
    void update() {
        GiftCertificate testData = GiftCertificate.builder().setId(5)
                .setName("test 7")
                .setPrice(7.0)
                .build();

        dao.update(testData);

        GiftCertificate actual = dao.findById(5).get();

        assertEquals(testData, actual);
    }

    @Test
    void delete() {
        GiftCertificate certificateToDelete = dao.findById(4).orElseThrow();
        dao.delete(certificateToDelete);

        GiftCertificate actual = dao.findById(4).orElse(null);

        assertNull(actual);
    }
}