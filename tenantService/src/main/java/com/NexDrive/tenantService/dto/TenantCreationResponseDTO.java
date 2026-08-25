package com.NexDrive.tenantService.dto;

import com.NexDrive.tenantService.enums.SubscriptionPlanTier;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class TenantCreationResponseDTO {

    private String name;

    private SubscriptionPlanTier plan;
}
