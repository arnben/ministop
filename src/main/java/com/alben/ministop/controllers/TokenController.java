package com.alben.ministop.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @PostMapping("/v1/oauth2/token")
    public ResponseEntity<TokenApiResponse> getToken(@RequestBody TokenApiRequest tokenApiRequest) {
        return ResponseEntity.ok(TokenApiResponse.builder().accessToken("abkfhkshfksjhjkkhk3hk3243423").build());
    }

    @Builder
    @Getter
    public static class TokenApiResponse {
        @JsonProperty("access_token")
        private String accessToken;
    }

    @Data
    public static class TokenApiRequest {
        @JsonProperty("client_id")
        private String clientId;

        @JsonProperty("client_secret")
        private String clientSecret;

        @JsonProperty("grant_type")
        private String grantType;
    }
}