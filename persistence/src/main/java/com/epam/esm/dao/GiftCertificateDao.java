package com.epam.esm.dao;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Identifiable;
import com.epam.esm.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class GiftCertificateDao extends AbstractDao<GiftCertificate> {

    private static final String TABLE_NAME = "gift_certificate";

    private static final String ADD_QUERY = "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES(?, ? ,?, ?, ?, ?)";
    private static final String FIND_BY_TAG_NAME_QUERY = "SELECT gift_certificate.id, gift_certificate.name, gift_certificate.description, gift_certificate.price, gift_certificate.duration, gift_certificate.create_date, gift_certificate.last_update_date FROM gift_certificate INNER JOIN tag_certificate ON gift_certificate.id = tag_certificate.gift_certificate_id LEFT JOIN tag ON tag_certificate.tag_id = tag.id where tag.name = ?";
    private static final String ADD_TAG = "INSERT INTO tag_certificate (gift_certificate_id, tag_id) VALUES (?, ?)";

    private CrudDao<Tag> tagDao;

    @Autowired
    public GiftCertificateDao(JdbcTemplate template) {
        super(template);
    }

    @Autowired
    public void setTagDao(CrudDao<Tag> tagDao) {
        this.tagDao = tagDao;
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public RowMapper<GiftCertificate> getRowMapper() {
        return (rs, rn) -> {
            GiftCertificate giftCertificate = new GiftCertificate();

            giftCertificate.setId(rs.getInt("id"));
            giftCertificate.setName(rs.getString("name"));
            giftCertificate.setDescription(rs.getString("description"));
            giftCertificate.setPrice(rs.getDouble("price"));
            giftCertificate.setDuration(rs.getInt("duration"));
            giftCertificate.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
            giftCertificate.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());

            return giftCertificate;
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
            //todo clear
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
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
        StringBuilder query = new StringBuilder("UPDATE gift_certificate SET");
        if (!certificate.getName().equals("")) {
            query.append("name = ?, ");
        }
        if (!certificate.getDescription().equals("")) {
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

    public void add(GiftCertificate certificate, List<? extends Identifiable> tags) {
        super.add(certificate);
        tags.stream().peek(tag -> {
            tagDao.add((Tag) tag);
            getTemplate().update(ADD_TAG, certificate.getId(), tag.getId());
        });
    }

    @Override
    public void update(GiftCertificate certificate, List<? extends Identifiable> tags) {
        super.update(certificate);
        tags.stream().peek(tag -> {
            if (tagDao.findByName(tag.getName()).isEmpty()) {
                tagDao.add((Tag) tag);
            }
        });
    }


}
