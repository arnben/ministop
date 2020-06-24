package com.alben.ministop.controllers;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;

@RestController
public class ClientController {

    @PostMapping("${ministop.admin.rootPath}/v1/client")
    public ResponseEntity<RegisterClientResponse> registerClient(@RequestBody RegisterClientRequest request) {
        return ResponseEntity.ok(RegisterClientResponse.builder().clientId("21421324se").clientSecret("bkhfkushakehska").build());
    }

    @PostMapping("${ministop.admin.rootPath}/v1/client/{clientId}/secret")
    public ResponseEntity<RegisterClientResponse> renewClie(@RequestBody RegisterClientRequest request) {
        return ResponseEntity.ok(RegisterClientResponse.builder().clientId("21421324se").clientSecret("bkhfkushakehska").build());
    }

    @Data
    @JsonNaming(SnakeCaseStrategy.class)
    public static class RegisterClientRequest {
        private String serviceName;
    }

    @Builder
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class RegisterClientResponse {
        private String clientId;
        private String clientSecret;
        private String serviceName;
    }

}
