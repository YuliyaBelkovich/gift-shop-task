package com.epam.esm.config;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.annotation.*;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application.properties")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer, WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext){
        servletContext.setInitParameter(
                "spring.profiles.active", "prod");
    }

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
}
