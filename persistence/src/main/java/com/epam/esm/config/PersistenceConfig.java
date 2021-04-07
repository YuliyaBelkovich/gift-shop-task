package com.epam.esm.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:persistence.properties")
@ComponentScan("com.epam.esm")
public class PersistenceConfig {

    @Value("${ds.driverClassName}")
    private String driverClassName;
    @Value("${ds.url}")
    private String url;
    @Value("${ds.username}")
    private  String username;
    @Value("${ds.password}")
    private String password;
    @Value("${ds.initPoolSize}")
    private int initPoolSize;


    @Bean(destroyMethod = "close")
    @Profile("prod")
    public BasicDataSource basicDataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driverClassName);
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setInitialSize(initPoolSize);
        return basicDataSource;
    }
    @Bean
    @Profile("prod")
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(basicDataSource());
    }

    @Bean
    @Profile("dev")
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db.sql/create-db.sql")
                .addScript("db.sql/insert-data.sql")
                .build();
        return db;
    }
    @Bean
    @Profile("dev")
    public JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }


}
