package com.NexDrive.tenantService.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TenantCreationResponseDTO {

    @NotBlank
    private String name;


}
