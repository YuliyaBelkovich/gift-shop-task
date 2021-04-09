package com.epam.esm.dao;

import com.epam.esm.dao.config.PersistenceTestConfig;
import com.epam.esm.models.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceTestConfig.class)
@Component
@ActiveProfiles("dev")
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
                .build();

        GiftCertificate actual = dao.findById(1).get();

        assertNotNull(actual);
        assertEquals(testData.getId(), actual.getId());
        assertEquals(testData.getName(), actual.getName());
        assertEquals(testData.getDescription(), actual.getDescription());
        assertEquals(testData.getPrice(), actual.getPrice());
        assertEquals(testData.getDuration(), actual.getDuration());
    }


    @Test
    void findWithParams() {
        Map<String, String> params = new HashMap<>();
        params.put("tag_name","test tag 1");
        params.put("name", "test");
        params.put("sort_by", "name");
        params.put("order", "DESC");

        GiftCertificate gc1 = GiftCertificate.builder().setId(1)
                .setName("test 1")
                .setDescription("description 1")
                .setPrice(1.0)
                .setDuration(1)
                .build();

        GiftCertificate gc2 = GiftCertificate.builder().setId(3)
                .setName("test 3")
                .setDescription("description 3")
                .setPrice(3.0)
                .setDuration(3)
                .build();

        List<GiftCertificate> actual = dao.findWithParams(params);

        assertNotNull(actual);
        assertEquals(2,actual.size());

        assertEquals(gc2.getId(), actual.get(0).getId());
        assertEquals(gc2.getName(), actual.get(0).getName());
        assertEquals(gc2.getDescription(), actual.get(0).getDescription());
        assertEquals(gc2.getPrice(), actual.get(0).getPrice());
        assertEquals(gc2.getDuration(), actual.get(0).getDuration());

        assertEquals(gc1.getId(), actual.get(1).getId());
        assertEquals(gc1.getName(), actual.get(1).getName());
        assertEquals(gc1.getDescription(), actual.get(1).getDescription());
        assertEquals(gc1.getPrice(), actual.get(1).getPrice());
        assertEquals(gc1.getDuration(), actual.get(1).getDuration());
    }


    @Test
    void add() {
        GiftCertificate testData = GiftCertificate.builder().setId(4)
                .setName("test 4")
                .setDescription("description 4")
                .setPrice(4.0)
                .setDuration(4)
                .build();

        dao.add(testData);

        GiftCertificate actual = dao.findById(4).get();

        assertNotNull(actual);

        assertEquals(testData.getId(), actual.getId());
        assertEquals(testData.getName(), actual.getName());
        assertEquals(testData.getDescription(), actual.getDescription());
        assertEquals(testData.getPrice(), actual.getPrice());
        assertEquals(testData.getDuration(), actual.getDuration());
    }

    @Test
    void update() {
        GiftCertificate testData = GiftCertificate.builder().setId(1)
                .setName("test 4")
                .setDescription("description 4")
                .setPrice(4.0)
                .setDuration(4)
                .build();

        dao.update(testData);

        GiftCertificate actual = dao.findById(1).get();

        assertNotNull(actual);

        assertEquals(testData.getId(), actual.getId());
        assertEquals(testData.getName(), actual.getName());
        assertEquals(testData.getDescription(), actual.getDescription());
        assertEquals(testData.getPrice(), actual.getPrice());
        assertEquals(testData.getDuration(), actual.getDuration());

    }

    @Test
    void delete(){
        dao.delete(1);

        GiftCertificate actual = dao.findById(1).orElse(null);

        assertNull(actual);
    }
}