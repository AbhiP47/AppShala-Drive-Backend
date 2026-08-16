package com.NexDrive.tenantService.controller;

import com.NexDrive.tenantService.dto.TenantCreationRequestDTO;
import com.NexDrive.tenantService.dto.TenantCreationResponseDTO;
import com.NexDrive.tenantService.service.TenantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {


    private TenantService tenantService;

    public TenantController(TenantService tenantService)
    {
        this.tenantService = tenantService;
    }

    @GetMapping()
    public ResponseEntity<TenantCreationResponseDTO> createTenant(
            @Valid  @RequestBody TenantCreationRequestDTO tenant
    )
    {
        TenantCreationResponseDTO response = tenantService.createTenant(tenant);

        return ResponseEntity.status(HttpStatus.CREATED).body(response) ;
    }

}