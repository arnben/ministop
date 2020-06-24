package com.alben.ministop.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.*;

@RestController
public class TokenController {
    @PostMapping("/v1/oauth2/token")
    public ResponseEntity<TokenApiResponse> getToken(@RequestBody TokenApiRequest tokenApiRequest) {
        return ResponseEntity.ok(TokenApiResponse.builder()
                .accessToken("abkfhkshfksjhjkkhk3hk3243423")
                .expiresAt(System.currentTimeMillis())
                .build());
    }

    @Builder
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class TokenApiResponse {
        private String accessToken;
        private long expiresAt;
    }

    @Data
    @JsonNaming(SnakeCaseStrategy.class)
    public static class TokenApiRequest {
        private String clientId;
        private String clientSecret;
        private String grantType;
    }
}