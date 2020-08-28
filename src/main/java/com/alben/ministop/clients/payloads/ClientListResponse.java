package com.alben.ministop.clients.payloads;

import lombok.*;

import java.util.*;

@Data
public class ClientListResponse {
    private Collection<String> clients;

    public ClientListResponse(Collection<String> clients) {
        this.clients = clients;
    }
}
