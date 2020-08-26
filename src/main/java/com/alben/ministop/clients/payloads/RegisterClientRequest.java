package com.alben.ministop.clients.payloads;

import com.alben.ministop.models.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import lombok.*;

import java.util.*;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RegisterClientRequest {
    private String name;
    private Collection<String> emails;

    public Client toClient() {
        return Client.builder().name(name).emails(emails).build();
    }
}
