package com.alben.ministop.clients;

import com.alben.ministop.clients.payloads.*;
import com.alben.ministop.models.Client;
import com.alben.ministop.clients.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
