package com.alben.ministop.clients.payloads;

import com.alben.ministop.models.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import lombok.*;

import java.util.*;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ClientResponse {
    private String name;
    private Collection<String> emails;

    public static ClientResponse of(Client client) {
        return ClientResponse.builder()
                .name(client.getName())
                .emails(client.getEmails())
                .build();
    }
}
