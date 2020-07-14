package com.alben.ministop.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

//@Configuration
//@EnableWebSecurity
//Reference: https://www.toptal.com/spring/spring-boot-oauth2-jwt-rest-protection
public class WebSecurityConfiguration {//extends WebSecurityConfigurerAdapter {

    @Value("${ministop.api.rootPath}")
    private String apiContextPath;

    @Value("${ministop.admin.rootPath}")
    private String adminContextPath;

    @Autowired
    private MinistopAuthenticationEntryPoint ministopAuthenticationEntryPoint;


//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and()
//            .authorizeRequests()
//            .antMatchers(apiContextPath + "/**").hasRole("USER")
//            .antMatchers(adminContextPath + "/**").hasRole("ADMIN")
//            .anyRequest().permitAll()
//        .and()
//            .exceptionHandling().authenticationEntryPoint(ministopAuthenticationEntryPoint);
//    }
}
