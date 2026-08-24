package com.NexDrive.tenantService.dto;

import com.NexDrive.tenantService.enums.SubscriptionPlanTier;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequestDTO {

    @NotNull
    private SubscriptionPlanTier plan;

}