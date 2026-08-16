package com.NexDrive.tenantService.service;

import com.NexDrive.tenantService.dto.TenantCreationRequestDTO;
import com.NexDrive.tenantService.dto.TenantCreationResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface TenantService {

    public TenantCreationResponseDTO createTenant(TenantCreationRequestDTO tenant);
}
