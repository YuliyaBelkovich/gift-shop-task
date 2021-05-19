package com.epam.esm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private AuthEntryPointJwt unauthorizedHandler;
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService,
                             AuthEntryPointJwt unauthorizedHandler,
                             AccessDeniedHandler accessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void setAuthenticationConfiguration(AuthenticationConfiguration authenticationConfiguration) {
        super.setAuthenticationConfiguration(authenticationConfiguration);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
//                .oauth2Client().and().oauth2ResourceServer().and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .accessDeniedHandler(accessDeniedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/gift-shop/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/certificates/**").permitAll()
                .anyRequest().authenticated();
//                .authorizeRequests(a -> a.antMatchers("/gift-shop/auth/**").permitAll()
//                        .antMatchers(HttpMethod.GET, "/certificates/**").permitAll()
//                        .antMatchers("/oauth2/**").permitAll()
//                        .antMatchers("/login/**").permitAll()
//                        .anyRequest().authenticated());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
