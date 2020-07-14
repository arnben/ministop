package com.alben.ministop.security.configuration;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;

public class MinistopAuthenticationEntryPoint {//implements AuthenticationEntryPoint {

    private HttpMessageConverter messageConverter;

    private MinistopAuthenticationEntryPoint() {
        this.messageConverter = new JsonbHttpMessageConverter();
    }

//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response,
//                         AuthenticationException authException) throws IOException, ServletException {
//        ServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
//        outputMessage.setStatusCode(HttpStatus.UNAUTHORIZED);
//
//        messageConverter.write(ApiError.builder().message(authException.getMessage()).build(),
//                MediaType.APPLICATION_JSON, outputMessage);
//    }

    @Builder
    @Getter
    private static class ApiError {
        private String message;
    }
}
