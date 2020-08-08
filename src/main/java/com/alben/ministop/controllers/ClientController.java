package com.alben.ministop.controllers;

import com.alben.ministop.models.Client;
import com.alben.ministop.services.ClientService;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;

@RestController
@RequestMapping("/${ministop.admin.rootPath}/v1/client")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<RegisterClientResponse> registerClient(
            @RequestBody RegisterClientRequest request) {
        Client client = clientService.register(request.getName());
        return ResponseEntity.accepted().body(RegisterClientResponse.of(client));
    }

    @PostMapping("/{clientId}/secret")
    public ResponseEntity<RegisterClientResponse> renewClient(
            @PathVariable String clientId,
            @RequestBody RegisterClientRequest request) {
        return ResponseEntity.ok(RegisterClientResponse.builder().clientId("21421324se").clientSecret("bkhfkushakehska").build());
    }

    @Data
    @JsonNaming(SnakeCaseStrategy.class)
    public static class RegisterClientRequest {
        private String name;
        private Collection<String> emails;
    }

    @Builder
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class RegisterClientResponse {
        private String clientId;
        private String clientSecret;
        private String serviceName;

        public static RegisterClientResponse of(Client client) {
            return RegisterClientResponse.builder()
                    .clientId(client.getId())
                    .clientSecret(client.getSecret())
                    .build();
        }
    }

}
