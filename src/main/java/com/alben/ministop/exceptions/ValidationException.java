package com.alben.ministop.exceptions;

public class ValidationException extends MinistopAppException {
    public ValidationException(ErrorDetails errorDetails, String ... args) {
        super(errorDetails, args);
    }
}
