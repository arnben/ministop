package com.alben.ministop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.MessageFormat;

@AllArgsConstructor
@Getter
public enum ErrorDetails {

    CLIENT_EXISTS(1, "Client with id '{0}' already exists."),
    CLIENT_NAME_HAS_SPACES(2, "Client name '{0}' has spaces.");

    private int code;
    private String messagePattern;

    public String getMessage(String ... args) {
        return MessageFormat.format(messagePattern, args);
    }

}
