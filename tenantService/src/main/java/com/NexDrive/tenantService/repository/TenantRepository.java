package com.NexDrive.tenantService.repository;

import com.NexDrive.tenantService.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {
}
