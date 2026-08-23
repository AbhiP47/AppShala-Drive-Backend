package com.NexDrive.tenantService.serviceImpl;

import com.NexDrive.tenantService.customAnnotation.TrackExecutionTime;
import com.NexDrive.tenantService.dto.TenantCreationRequestDTO;
import com.NexDrive.tenantService.dto.TenantCreationResponseDTO;
import com.NexDrive.tenantService.service.TenantService;

public class TenantServiceImpl implements TenantService {
    @Override
    @TrackExecutionTime(
            warnAfter = 2000,
            operationName = "creating new tenant"
    )
    public TenantCreationResponseDTO createTenant(TenantCreationRequestDTO tenant) {
        return null;
    }
}
