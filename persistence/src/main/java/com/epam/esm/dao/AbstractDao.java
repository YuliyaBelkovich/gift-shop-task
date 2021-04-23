package com.epam.esm.dao;

import com.epam.esm.models.Identifiable;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.*;


public abstract class AbstractDao<T extends Identifiable> implements CrudDao<T> {

}
