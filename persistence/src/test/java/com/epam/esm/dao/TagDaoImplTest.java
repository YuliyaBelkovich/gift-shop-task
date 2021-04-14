package com.epam.esm.dao;

import com.epam.esm.dao.config.PersistenceTestConfig;
import com.epam.esm.models.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.junit.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceTestConfig.class)
class TagDaoImplTest {
    @Autowired
    private TagDao dao;

    @Test
    public void findById() {
        Tag testData = Tag.builder().setId(1).setName("test tag 1").build();

        Tag actual = dao.findById(1).orElse(null);

        assertThat(testData, is(actual));
    }

    @Test
    public void findAll() {
        Tag firstTag = Tag.builder().setId(1).setName("test tag 1").build();
        Tag secondTag = Tag.builder().setId(2).setName("test tag 2").build();
        List<Tag> actual = dao.findAll();

        assertThat(firstTag, is(actual.get(0)));
        assertThat(secondTag, is(actual.get(1)));
    }

    @Test
    public void add() {
        Tag testData = Tag.builder().setId(4).setName("test tag 4").build();

        dao.add(testData);

        Tag actual = dao.findById(4).orElse(null);

        assertThat(testData, is(actual));
    }

    @Test
    public void delete() {
        dao.delete(3);

        Tag actual = dao.findById(3).orElse(null);

        assertNull(actual);
    }
}