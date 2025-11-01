package com.appshala.userService.Exception;

import com.appshala.userService.Payloads.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFound(UsernameNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now()) // Set current time
                .status(status.value())          // Set status code
                .error(status.getReasonPhrase()) // Set error phrase
                .message(ex.getMessage())        // Use the exception message
                // Extracts the URI path
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(error, status);
    }
}
