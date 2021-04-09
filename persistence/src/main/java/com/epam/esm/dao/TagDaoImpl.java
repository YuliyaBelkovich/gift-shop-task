package com.epam.esm.dao;

import com.epam.esm.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {

    private static final String TABLE_NAME = "tag";

    private static final String ADD_QUERY = "INSERT INTO tag (name) VALUES(?)";
    private static final String FIND_TAG_BY_GIFT_ID_QUERY = "SELECT * FROM tag JOIN tag_certificate ON tag.id = tag_certificate.tag_id WHERE gift_certificate_id = ?";

    @Autowired
    public TagDaoImpl(JdbcTemplate template) {
        super(template);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public PreparedStatementCreator getCreatorForAdd(Tag identity) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(ADD_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, identity.getName());
            return ps;
        };
    }

    @Override
    public RowMapper<Tag> getRowMapper() {
        return (rs, rowNum) -> Tag.builder()
                .setId(rs.getInt("id"))
                .setName(rs.getString("name"))
                .build();
    }

    @Override
    public PreparedStatementCreator getCreatorForUpdate(Tag identity) {
        return null;
    }

    public List<Tag> findByGiftId(int id) {
        return executeQuery(FIND_TAG_BY_GIFT_ID_QUERY, getRowMapper(), id);
    }

}
