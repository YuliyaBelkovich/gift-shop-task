package com.epam.esm.dao.impl;

import static org.hamcrest.junit.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.config.PersistenceTestConfig;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(classes = PersistenceTestConfig.class)
class OrderDaoImplTest {

    @Autowired
    private OrderDao dao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GiftCertificateDao certificateDao;

    @Test
    public void findById() {
        List<GiftCertificate> certificateList = new ArrayList<>();
        certificateList.add(certificateDao.findById(1).orElseThrow());
        Order testData = Order.builder().setId(1)
                .setCreateDate(LocalDateTime.of(2021, 1, 1, 0, 0, 1))
                .setCost(1)
                .setUser(userDao.findById(1).orElseThrow())
                .setCertificates(certificateList)
                .build();

        Order actual = dao.findById(testData.getId()).orElseThrow();

        assertThat(testData, is(actual));
    }

    @Test
    public void findAll() {
        List<GiftCertificate> certificateList = new ArrayList<>();
        certificateList.add(certificateDao.findById(1).orElseThrow());
        Order testData = Order.builder().setId(1)
                .setCreateDate(LocalDateTime.of(2021, 1, 1, 0, 0, 1))
                .setCost(1)
                .setUser(userDao.findById(1).orElseThrow())
                .setCertificates(certificateList)
                .build();

        List<GiftCertificate> certificateList2 = new ArrayList<>();
        certificateList2.add(certificateDao.findById(2).orElseThrow());

        Order testData2 = Order.builder().setId(2)
                .setCreateDate(LocalDateTime.of(2021, 1, 1, 0, 0, 1))
                .setCost(2)
                .setUser(userDao.findById(2).orElseThrow())
                .setCertificates(certificateList2)
                .build();

        List<Order> expected = new ArrayList<>();
        expected.add(testData);
        expected.add(testData2);

        List<Order> actual = dao.findAll(1, 2).getResponses();

        assertThat(expected, is(actual));
    }

    @Test
    public void add() {
        Order testData = Order.builder()
                .setCreateDate(LocalDateTime.of(2021, 1, 1, 0, 0, 1))
                .setCost(7)
                .setUser(userDao.findById(1).orElseThrow())
                .build();

        dao.add(testData);

        Order actual = dao.findById(7).orElseThrow();

        assertThat(testData, is(actual));
    }

    @Test
    public void delete() {
        Order orderToDelete = dao.findById(6).orElseThrow();

        dao.delete(orderToDelete);

        Order actual = dao.findById(6).orElse(null);

        assertNull(actual);
    }

}