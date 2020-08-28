package com.alben.ministop.clients;

import com.alben.ministop.clients.payloads.*;
import com.alben.ministop.exceptions.*;
import com.alben.ministop.models.Client;
import com.alben.ministop.clients.services.ClientService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.*;
import java.util.*;
import java.util.stream.*;

@RestController
@RequestMapping("/${ministop.admin.rootPath}/v1/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponse> registerClient(
            @RequestBody RegisterClientRequest request) {
        Client client = clientService.register(request.toClient());
        return ResponseEntity.accepted().body(ClientResponse.of(client));
    }

    @GetMapping
    public ResponseEntity<ClientListResponse> getAllClients() {
        return ResponseEntity.ok(new ClientListResponse(clientService.getAllClientNames()));
    }

    @Data
    private static class ClientListResponse {
        @Singular private Collection<String> clients;
        public ClientListResponse(Collection<String> clients) {
            this.clients = clients;
        }
    }
}
