package com.alben.ministop.exceptions;

public class MinistopAppException extends RuntimeException {

    private ErrorDetails errorDetails;

    public MinistopAppException(ErrorDetails errorDetails, String ... args) {
        super(errorDetails.getMessage(args));
        this.errorDetails = errorDetails;
    }

    public MinistopAppException(ErrorDetails errorDetails, Exception e, String ... args) {
        super(errorDetails.getMessage(args), e);
        this.errorDetails = errorDetails;
    }

    public final int getCode() {
        return errorDetails.getCode();
    }
}
