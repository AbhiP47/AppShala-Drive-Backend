package com.NexDrive.tenantService.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class TenantCreationRequestDTO {

    @NotBlank
    private String name;

}
