package com.epam.esm.dao;

import com.epam.esm.models.Identifiable;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.SQLException;
import java.util.*;


public abstract class AbstractDao<T extends Identifiable> implements CrudDao<T> {

    private static final String FIND_ALL_QUERY = "SELECT * FROM %s";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM %s WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM %s WHERE id = ?";
    private static final String FIND_BY_NAME = "SELECT* FROM %s WHERE name = ?";

    private final JdbcTemplate template;

    public AbstractDao(JdbcTemplate template) {
        this.template = template;
    }

    protected void executeUpdate(String sql, Object... args) {
        template.update(sql, args);
    }

    protected List<T> executeQuery(String sql, RowMapper<T> rowMapper, Object... args) {
        return template.query(sql, rowMapper, args);
    }

    protected abstract String getTableName();

    protected abstract RowMapper<T> getRowMapper();

    protected abstract PreparedStatementCreator getCreatorForAdd(T identity);

    protected abstract PreparedStatementCreator getCreatorForUpdate(T identity);

    @Override
    public List<T> findAll() throws BadSqlGrammarException {
        return template.query(String.format(FIND_ALL_QUERY, getTableName()), getRowMapper());
    }

    @Override
    public Optional<T> findById(int id) throws BadSqlGrammarException {
            return template.query(String.format(FIND_BY_ID_QUERY, getTableName()), getRowMapper(), id).stream().findAny();
    }

    public Optional<T> findByName(String name)  {
        return template.query(String.format(FIND_BY_NAME, getTableName()), getRowMapper(), name).stream().findAny();
    }

    @Override
    public void delete(int id) {
        template.update(String.format(DELETE_QUERY, getTableName()), id);
    }


    public void add(T identity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(getCreatorForAdd(identity), keyHolder);
        try {
            identity.setId((int) keyHolder.getKeys().get("id"));
        } catch (NullPointerException e) {
            identity.setId(keyHolder.getKey().intValue());
        }
    }

    public void update(T identity) {
        template.update(getCreatorForUpdate(identity));
    }
}
