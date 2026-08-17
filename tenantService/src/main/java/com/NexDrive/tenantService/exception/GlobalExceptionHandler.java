package com.NexDrive.tenantService.exception;

import com.NexDrive.tenantService.dto.ErrorResponseDTO;
import com.NexDrive.tenantService.dto.ValidationErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex ,
            HttpServletRequest httpServletRequest
    )
    {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_GATEWAY.getReasonPhrase(),
                "Invalid request body . Please check your JSON format and data types",
                httpServletRequest.getRequestURI()
        );

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseDTO);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex ,
            HttpServletRequest httpServletRequest
    )
    {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_GATEWAY.getReasonPhrase(),
                "Invalid value for parameter '" + ex.getName() + "' . Please check the required data type.",
                httpServletRequest.getRequestURI()
        );

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseDTO);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateResourceException(
            DuplicateResourceException ex ,
            HttpServletRequest httpServletRequest
    )
    {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                ZonedDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                httpServletRequest.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponseDTO);
    }
}
