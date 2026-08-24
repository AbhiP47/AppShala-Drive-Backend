package com.NexDrive.tenantService.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantCreationRequestDTO {

    @NotBlank
    @Size(max = 150)
    private String name;

    @NotNull
    private UUID ownerId;

    @NotBlank
    @Size(max = 20)
    private String phone;
}