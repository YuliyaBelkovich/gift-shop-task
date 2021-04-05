package com.epam.esm.dao;

import com.epam.esm.models.Identifiable;
import com.epam.esm.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class TagDao extends AbstractDao<Tag> {

    private static final String TABLE_NAME = "tag";

    private static final String ADD_QUERY = "INSERT INTO tag (name) VALUES(?)";
    private static final String FIND_TAG_BY_GIFT_ID_QUERY = "SELECT * FROM tag JOIN tag_certificate ON tag.id = tag_certificate.tag_id WHERE gift_certificate_id = ?";

    @Autowired
    public TagDao(JdbcTemplate template) {
        super(template);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public PreparedStatementCreator getCreatorForAdd(Tag identity) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(ADD_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, identity.getName());
            return ps;
        };
    }

    @Override
    public RowMapper<Tag> getRowMapper() {
        return new RowMapper<Tag>() {
            @Override
            public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
                Tag tag = new Tag();

                tag.setId(rs.getInt("id"));
                tag.setName(rs.getString("name"));

                return tag;
            }
        };
    }

    @Override
    public PreparedStatementCreator getCreatorForUpdate(Tag identity) {
        return null;
    }

    public List<Tag> findByGiftId(int id) {
        return getTemplate().query(FIND_TAG_BY_GIFT_ID_QUERY, getRowMapper(), id);
    }

    @Override
    public void add(Tag identity, List<? extends Identifiable> list) {

    }

    @Override
    public void update(Tag identity, List<? extends Identifiable> list) {
    }
}
