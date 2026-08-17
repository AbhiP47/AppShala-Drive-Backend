package com.NexDrive.tenantService.exception;

import com.NexDrive.tenantService.dto.ErrorResponseDTO;
import com.NexDrive.tenantService.dto.ValidationErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception e , HttpServletRequest httpServletRequest)
    {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(
                ZonedDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                e.getMessage(),
                httpServletRequest.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseDTO);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException e ,
                                                                  HttpServletRequest httpServletRequest)
    {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(
                ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                httpServletRequest.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDTO> handleValidationException(
            MethodArgumentNotValidException e ,
            HttpServletRequest httpServletRequest
    )
    {
        Map<String,String> fieldErrors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
            fieldErrors.put(error.getField(),error.getDefaultMessage())
        );

        ValidationErrorResponseDTO responseDTO = new ValidationErrorResponseDTO(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation Failed",
                httpServletRequest.getRequestURI(),
                fieldErrors
        );

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseDTO);
    }
}
