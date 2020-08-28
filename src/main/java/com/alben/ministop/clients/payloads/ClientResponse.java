package com.alben.ministop.clients.payloads;

import com.alben.ministop.models.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import lombok.*;

import java.util.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class ClientResponse {
    private String name;
    private Collection<String> emails;
    private String key;

    public static ClientResponse of(Client client) {
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setEmails(client.getEmails());
        clientResponse.setKey(client.getKey());
        clientResponse.setName(client.getName());
        return clientResponse;
    }
}
