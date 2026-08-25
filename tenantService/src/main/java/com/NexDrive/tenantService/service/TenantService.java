package com.NexDrive.tenantService.service;

import com.NexDrive.tenantService.dto.SubscriptionRequestDTO;
import com.NexDrive.tenantService.dto.TenantCreationRequestDTO;
import com.NexDrive.tenantService.dto.TenantCreationResponseDTO;

public interface TenantService {

    public TenantCreationResponseDTO createTenant(TenantCreationRequestDTO tenantDTO , SubscriptionRequestDTO subscriptionRequestDTO);
}
