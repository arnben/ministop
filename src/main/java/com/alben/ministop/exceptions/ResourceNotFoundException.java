package com.alben.ministop.exceptions;

public class ResourceNotFoundException extends MinistopAppException {
    public ResourceNotFoundException(ErrorDetails errorDetails, String ... args) {
        super(errorDetails, args);
    }
}
