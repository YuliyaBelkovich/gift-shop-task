package com.epam.esm.config;

import org.springframework.context.annotation.*;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
@Import(ServiceConfig.class)
public class WebConfig implements WebMvcConfigurer, WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext){
        servletContext.setInitParameter(
                "spring.profiles.active", "prod");
    }

}
