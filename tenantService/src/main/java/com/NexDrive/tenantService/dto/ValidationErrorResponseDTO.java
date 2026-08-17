package com.NexDrive.tenantService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Map;

@AllArgsConstructor
@Getter
public class ValidationErrorResponseDTO {

    private ZonedDateTime timestamp;
    private int statusCode;
    private String error;
    private String message;
    private String path;
    private Map<String , String> fieldErrors;
}
