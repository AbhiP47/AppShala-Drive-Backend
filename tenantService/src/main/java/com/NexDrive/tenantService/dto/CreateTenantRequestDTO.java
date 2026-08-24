package com.NexDrive.tenantService.dto;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class CreateTenantRequestDTO {

    @Valid
    private TenantCreationRequestDTO tenant;

    @Valid
    private SubscriptionRequestDTO subscription;

}