package com.NexDrive.tenantService.controller;

import com.NexDrive.tenantService.dto.CreateTenantRequestDTO;
import com.NexDrive.tenantService.dto.SubscriptionRequestDTO;
import com.NexDrive.tenantService.dto.TenantCreationRequestDTO;
import com.NexDrive.tenantService.dto.TenantCreationResponseDTO;
import com.NexDrive.tenantService.model.Subscription;
import com.NexDrive.tenantService.service.TenantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {


    private final TenantService tenantService;

    public TenantController(TenantService tenantService)
    {
        this.tenantService = tenantService;
    }

    @PostMapping()
    public ResponseEntity<TenantCreationResponseDTO> createTenant(
            @Valid  @RequestBody CreateTenantRequestDTO createTenantRequestDTO
            )
    {
        TenantCreationResponseDTO response = tenantService.createTenant(createTenantRequestDTO.getTenant() , createTenantRequestDTO.getSubscription());

        return ResponseEntity.status(HttpStatus.CREATED).body(response) ;
    }

}