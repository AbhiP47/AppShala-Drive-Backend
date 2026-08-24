package com.NexDrive.tenantService.mapper;

import com.NexDrive.tenantService.dto.TenantCreationRequestDTO;
import com.NexDrive.tenantService.dto.TenantCreationResponseDTO;
import com.NexDrive.tenantService.enums.TenantStatus;
import com.NexDrive.tenantService.model.Tenant;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,  // Ignores unmapped fields without build warnings
        imports = {TenantStatus.class}
)public interface TenantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", expression = "java(TenantStatus.ACTIVE)")
    @Mapping(target = "storageUsedBytes", constant = "0L")
    Tenant toEntity(TenantCreationRequestDTO requestDto);

    TenantCreationResponseDTO toCreateResponseDto(Tenant tenant);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    void updateEntityFromDto(TenantCreationRequestDTO requestDto, @MappingTarget Tenant tenant);
}
