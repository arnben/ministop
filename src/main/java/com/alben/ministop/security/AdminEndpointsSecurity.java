package com.alben.ministop.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class AdminEndpointsSecurity implements WebMvcConfigurer {

    @Value("${ministop.admin.rootPath}")
    private String adminContextPath;

    @Autowired
    private AdminEndpointInterceptor adminEndpointInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminEndpointInterceptor).addPathPatterns(adminContextPath + "/**");
    }

    @Slf4j
    @Component
    public static class AdminEndpointInterceptor implements HandlerInterceptor {

        @Value("${ministop.admin.secret}")
        private String adminKey;

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            log.info("Calling admin endpoint.");
            String inputKey = request.getHeader("adminKey");
            if(adminKey.equals(inputKey)) {
                return true;
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

        }
    }
}
