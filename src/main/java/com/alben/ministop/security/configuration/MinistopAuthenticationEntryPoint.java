package com.alben.ministop.security.configuration;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MinistopAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private HttpMessageConverter messageConverter;

    private MinistopAuthenticationEntryPoint() {
        this.messageConverter = new JsonbHttpMessageConverter();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
        outputMessage.setStatusCode(HttpStatus.UNAUTHORIZED);

        messageConverter.write(ApiError.builder().message(authException.getMessage()).build(),
                MediaType.APPLICATION_JSON, outputMessage);
    }

    @Builder
    @Getter
    private static class ApiError {
        private String message;
    }
}
