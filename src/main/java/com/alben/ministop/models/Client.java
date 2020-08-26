package com.alben.ministop.models;

import lombok.*;

import java.util.*;

@Data
@Builder
public class Client {
    private String name;
    private Collection<String> emails;
}
