package com.epam.esm.dao;

import com.epam.esm.dao.config.PersistenceTestConfig;
import com.epam.esm.models.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceTestConfig.class)
@Component
class TagDaoImplTest {
    @Autowired
    private TagDao dao;

    @Test
    public void findById() {
        Tag testData = Tag.builder().setId(1).setName("test tag 1").build();

        Tag actual = dao.findById(1).orElse(null);

        assertNotNull(actual);
        assertEquals(testData.getId(), actual.getId());
        assertEquals(testData.getName(), actual.getName());
    }

    @Test
    public void findAll() {
        Tag firstTag = Tag.builder().setId(1).setName("test tag 1").build();
        Tag secondTag = Tag.builder().setId(2).setName("test tag 2").build();
        List<Tag> actual = dao.findAll();

        assertNotNull(actual);
        assertEquals(firstTag.getId(), actual.get(0).getId());
        assertEquals(firstTag.getName(), actual.get(0).getName());

        assertEquals(secondTag.getId(), actual.get(1).getId());
        assertEquals(secondTag.getName(), actual.get(1).getName());
    }

    @Test
    public void add(){
        Tag testData = Tag.builder().setId(4).setName("test tag 4").build();

        dao.add(testData);

        Tag actual = dao.findById(4).orElse(null);

        assertNotNull(actual);

        assertEquals(testData.getId(), actual.getId());
        assertEquals(testData.getName(), actual.getName());
    }

    @Test
    public void delete(){
        dao.delete(3);

        Tag actual = dao.findById(3).orElse(null);

        assertNull(actual);
    }
}