package com.epam.esm.dao.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EntityScan("com.epam.esm")
@ComponentScan("com.epam.esm")
public class PersistenceTestConfig {
}
