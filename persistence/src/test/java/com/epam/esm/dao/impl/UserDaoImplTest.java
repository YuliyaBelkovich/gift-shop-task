package com.epam.esm.dao.impl;

import static org.hamcrest.junit.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.config.PersistenceTestConfig;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Order;
import com.epam.esm.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest(classes = PersistenceTestConfig.class)
class UserDaoImplTest {

    @Autowired
    private UserDao dao;
    @Autowired
    private GiftCertificateDao certificateDao;

    @Test
    void findById() {
        User testData = User.builder()
                .setId(1)
                .setName("test 1")
                .setEmail("test1@email.com")
                .setPassword("test 1 password")
                .build();

        User actual = dao.findById(1).orElseThrow();

        assertThat(actual, is(testData));
    }

    @Test
    void findUserOrders() {
        List<GiftCertificate> certificateList = new ArrayList<>();
        certificateList.add(certificateDao.findById(1).orElseThrow());
        Order testData = Order.builder().setId(1)
                .setCreateDate(LocalDateTime.of(2021, 1, 1, 0, 0, 1))
                .setCost(1)
                .setUser(dao.findById(1).orElseThrow())
                .setCertificates(certificateList)
                .build();

        List<Order> expected = new ArrayList<>();
        expected.add(testData);

        List<Order> actual = dao.findUserOrders(1, 1, 1).getResponses();

        assertThat(expected, is(actual));
    }

    @Test
    void findUserOrderById() {
        List<GiftCertificate> certificateList = new ArrayList<>();
        certificateList.add(certificateDao.findById(1).orElseThrow());
        Order testData = Order.builder().setId(1)
                .setCreateDate(LocalDateTime.of(2021, 1, 1, 0, 0, 1))
                .setCost(1)
                .setUser(dao.findById(1).orElseThrow())
                .setCertificates(certificateList)
                .build();

        Order actual = dao.findUserOrderById(1, 1).orElseThrow();

        assertThat(testData, is(actual));
    }
}