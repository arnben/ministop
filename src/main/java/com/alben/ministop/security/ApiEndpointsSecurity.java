package com.alben.ministop.security;

import com.alben.ministop.clients.repositories.*;
import com.alben.ministop.clients.services.*;
import com.alben.ministop.exceptions.*;
import com.alben.ministop.models.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class ApiEndpointsSecurity implements WebMvcConfigurer {

    @Value("${ministop.api.rootPath}")
    private String apiRootPath;

    @Autowired
    private ApiEndpointInterceptor apiEndpointInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiEndpointInterceptor).addPathPatterns("/" + apiRootPath + "/**");
    }

    @Slf4j
    @Component
    public static class ApiEndpointInterceptor implements HandlerInterceptor {

        @Autowired
        private ClientService clientService;

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            log.info("Calling api endpoint.");
            String token = request.getHeader("apiKey");
            String clientName = request.getHeader("client");
            String method = request.getMethod();
            if(StringUtils.isNotBlank(token) && StringUtils.isNotBlank(clientName)) {
                try {
                    Client client = clientService.getClient(clientName);
                    if(token.equals(client.getKey()) && "GET".equalsIgnoreCase(method))
                        return true;
                    log.info("Invalid key for " + clientName + " method: " + method);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                } catch(ResourceNotFoundException e) {
                    log.info("Unknown client: " + clientName);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
            } else {
                log.info("No apiKey for: " + clientName);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

        }
    }
}
