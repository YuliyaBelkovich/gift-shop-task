package com.epam.esm;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class Application {

    @Autowired
    private DataSource dataSource;

    @Value("${spring.jpa.show-sql}")
    private String showSql;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;
    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String dialect;
    @Value("${spring.jpa.properties.hibernate.current_session_context_class}")
    private String contextClass;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter adapter) {
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource);
//        entityManagerFactoryBean.setJpaVendorAdapter(adapter);
//        entityManagerFactoryBean.setPackagesToScan("com.epam.esm");
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect", dialect);
//        properties.put("hibernate.show_sql", showSql);
//        properties.put("hibernate.hbm2ddl.auto","create-drop");
//        properties.put("hibernate.ejb.naming_strategy","org.hibernate.cfg.ImprovedNamingStrategy");
//        entityManagerFactoryBean.setJpaProperties(properties);
//        return entityManagerFactoryBean;
//    }
//
//    @Bean
//    public JpaVendorAdapter jpaVendorAdapter(){
//        return new HibernateJpaVendorAdapter();
//    }
//
//    @Bean
//    JpaTransactionManager getJpaTransactionManager(EntityManagerFactory entityManagerFactory){
//        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
//        return jpaTransactionManager;
//    }
//
//    @Bean
//    public SessionFactory getSessionFactory(DataSource dataSource) throws IOException {
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect", dialect);
//        properties.put("hibernate.show_sql", showSql);
//        properties.put("current_session_context_class", contextClass);
//        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
//
//        factoryBean.setPackagesToScan("com.epam.esm");
//        factoryBean.setDataSource(dataSource);
//        factoryBean.setHibernateProperties(properties);
//
//        factoryBean.afterPropertiesSet();
//        return factoryBean.getObject();
//    }
//
//    @Bean
//    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
//        return new HibernateTransactionManager(sessionFactory);
//    }
//
//
}