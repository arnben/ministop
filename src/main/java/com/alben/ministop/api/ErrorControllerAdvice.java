package com.alben.ministop.api;

import com.alben.ministop.exceptions.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@Slf4j
public class ErrorControllerAdvice {

    @Builder
    @Getter
    private static class ErrorResponse {
        private int code;
        private String message;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException validationException) {
        log.error(validationException.getMessage(), validationException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder()
                .code(validationException.getCode())
                .message(validationException.getMessage()).build());
    }
}
