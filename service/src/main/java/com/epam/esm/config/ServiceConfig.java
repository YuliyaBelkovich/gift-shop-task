package com.epam.esm.config;

import com.epam.esm.config.PersistenceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:service.properties")
@ComponentScan("com.epam.esm")
@Import(PersistenceConfig.class)
public class ServiceConfig {

}
