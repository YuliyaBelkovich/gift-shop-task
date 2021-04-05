package com.epam.esm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GiftTagDao {

    private static final String ADD_QUERY = "INSERT INTO tag_certificate (gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String GET_ONE_QUERY = "SELECT * FROM tag_certificate WHERE gift_certificate_id = ? AND tag_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM tag_certificate  WHERE gift_certificate_id = ? AND tag_id = ?";

    private JdbcTemplate template;

    @Autowired
    public GiftTagDao(JdbcTemplate template) {
        this.template = template;
    }

    public RowMapper<Map<Integer, Integer>> getRowMapper() {
        return (rs, rowNum) -> {
            Map<Integer, Integer> gift_tag = new HashMap<>();
            gift_tag.put(rs.getInt(1), rs.getInt(2));
            return gift_tag;
        };
    }

    public void add(int certificateId, int tagId) {
        if (template.query(GET_ONE_QUERY, getRowMapper(), certificateId, tagId).isEmpty()) {
            template.update(ADD_QUERY, certificateId, tagId);
        }
    }

    public void delete(int certificateId, int tagId) {
        template.update(DELETE_QUERY, certificateId, tagId);
    }
}
