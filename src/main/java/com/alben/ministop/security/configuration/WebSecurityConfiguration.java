package com.alben.ministop.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableWebSecurity
//Reference: https://www.toptal.com/spring/spring-boot-oauth2-jwt-rest-protection
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${ministop.api.rootPath}")
    private String apiContextPath;

    @Value("${ministop.admin.rootPath}")
    private String adminContextPath;

    @Autowired
    private MinistopAuthenticationEntryPoint ministopAuthenticationEntryPoint;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers(apiContextPath + "/**").hasRole("USER")
            .antMatchers(adminContextPath + "/**").hasRole("ADMIN")
            .anyRequest().permitAll()
        .and()
            .exceptionHandling().authenticationEntryPoint(ministopAuthenticationEntryPoint);
    }
}
