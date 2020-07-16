package com.alben.ministop.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Client {
    private String id;
    private String secret;
}
