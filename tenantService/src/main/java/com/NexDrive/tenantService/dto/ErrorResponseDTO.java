package com.NexDrive.tenantService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class ErrorResponseDTO {

    private ZonedDateTime timestamp;
    private int statusCode;
    private String error;
    private String message;
    private String path;


}
