package com.epam.esm.dao;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate> implements GiftCertificateDao {

    private static final String TABLE_NAME = "gift_certificate";

    private static final String ADD_QUERY = "INSERT INTO gift_certificate (name, description, price, duration) VALUES(?, ? ,?, ?)";
    private static final String FIND_BY_TAG_NAME_QUERY = "SELECT gift_certificate.id, gift_certificate.name, gift_certificate.description, gift_certificate.price, gift_certificate.duration, gift_certificate.create_date, gift_certificate.last_update_date FROM gift_certificate INNER JOIN tag_certificate ON gift_certificate.id = tag_certificate.gift_certificate_id LEFT JOIN tag ON tag_certificate.tag_id = tag.id where tag.name = ?";
    private static final String ADD_TAG = "INSERT INTO tag_certificate (gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String SEARCH_BY_FIELD_QUERY = "SELECT * FROM gift_certificate WHERE %s LIKE ?";
    private static final String SORT_QUERY = "SELECT * FROM gift_certificate ORDER BY %s %s";
    private TagDao tagDao;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate template) {
        super(template);
    }

    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public RowMapper<GiftCertificate> getRowMapper() {
        return (rs, rn) -> {
            return GiftCertificate.builder()
                    .setId(rs.getInt("id"))
                    .setName(rs.getString("name"))
                    .setDescription(rs.getString("description"))
                    .setPrice(rs.getDouble("price"))
                    .setDuration(rs.getInt("duration"))
                    .setCreateDate(rs.getTimestamp("create_date").toLocalDateTime())
                    .setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime())
                    .build();
        };
    }

    @Override
    public PreparedStatementCreator getCreatorForAdd(GiftCertificate certificate) {

        return con -> {
            PreparedStatement ps = con
                    .prepareStatement(ADD_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setDouble(3, certificate.getPrice());
            ps.setInt(4, certificate.getDuration());
            return ps;
        };
    }

    @Override
    public PreparedStatementCreator getCreatorForUpdate(GiftCertificate certificate) {
        return con -> {
            int i = 0;
            PreparedStatement ps = con.prepareStatement(prepareUpdate(certificate));
            if (!certificate.getName().equals("")) {
                ps.setString(++i, certificate.getName());
            }
            if (!certificate.getDescription().equals("")) {
                ps.setString(++i, certificate.getDescription());
            }
            if (certificate.getPrice() != 0) {
                ps.setDouble(++i, certificate.getPrice());
            }
            if (certificate.getDuration() != 0) {
                ps.setInt(++i, certificate.getDuration());
            }
            ps.setInt(++i, certificate.getId());
            return ps;
        };
    }

    private String prepareUpdate(GiftCertificate certificate) {
        StringBuilder query = new StringBuilder("UPDATE gift_certificate SET ");
        if (StringUtils.isNotBlank(certificate.getName())) {
            query.append("name = ?, ");
        }
        if (StringUtils.isNotBlank(certificate.getDescription())) {
            query.append("description = ?, ");
        }
        if (certificate.getPrice() != 0) {
            query.append("price = ?, ");
        }
        if (certificate.getDuration() != 0) {
            query.append("duration = ?, ");
        }
        query.delete(query.length() - 2, query.length() - 1);
        query.append(" WHERE id = ?");
        return query.toString();
    }

    public List<GiftCertificate> findByTag(Tag tag) {
        return getTemplate().query(FIND_BY_TAG_NAME_QUERY, getRowMapper(), tag.getName());
    }

    public void add(GiftCertificate certificate, List<Tag> tags) {
        super.add(certificate);
        tags.forEach(tag -> {
            Optional<Tag> search = tagDao.findByName(tag.getName());
            if (search.isEmpty()) {
                tagDao.add(tag);
            } else {
                tag = search.get();
            }
            executeUpdate(ADD_TAG, certificate.getId(), tag.getId());

        });
    }

    @Override
    public void update(GiftCertificate certificate, List<Tag> tags) {
        super.update(certificate);
        tags.forEach(tag -> {
            Optional<Tag> search = tagDao.findByName(tag.getName());
            if (search.isEmpty()) {
                tagDao.add(tag);
            } else {
                tag = search.get();
            }
            try {
                executeUpdate(ADD_TAG, certificate.getId(), tag.getId());
            } catch (Exception ignored) {
            }
        });
    }

    public List<GiftCertificate> searchByPartOfField(String field, String value) {
        return getTemplate().query(String.format(SEARCH_BY_FIELD_QUERY, field), getRowMapper(), "%".concat(value.concat("%")));
    }

    public List<GiftCertificate> sort(String field, String order) {
        return getTemplate().query(String.format(SORT_QUERY, field, order), getRowMapper());
    }
}
