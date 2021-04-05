package com.epam.esm.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

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
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(basicDataSource());
    }


}
