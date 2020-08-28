package com.alben.ministop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.MessageFormat;

@AllArgsConstructor
@Getter
public enum ErrorDetails {

    CLIENT_EXISTS(1, "Client with id '%s' already exists."),
    CLIENT_NAME_HAS_SPACES(2, "Client name '%s' has spaces."),
    CLIENT_NOT_FOUND(3, "Client name '%s' does not exist.");

    private int code;
    private String messagePattern;

    public String getMessage(String ... args) {
        return String.format(messagePattern, args);
    }

}
