package com.NexDrive.tenantService.serviceImpl;

import com.NexDrive.tenantService.customAnnotation.TrackExecutionTime;
import com.NexDrive.tenantService.dto.SubscriptionRequestDTO;
import com.NexDrive.tenantService.dto.TenantCreationRequestDTO;
import com.NexDrive.tenantService.dto.TenantCreationResponseDTO;
import com.NexDrive.tenantService.enums.SubscriptionStatus;
import com.NexDrive.tenantService.enums.TenantStatus;
import com.NexDrive.tenantService.helper.SubscriptionInfoHelper;
import com.NexDrive.tenantService.model.Subscription;
import com.NexDrive.tenantService.model.Tenant;
import com.NexDrive.tenantService.repository.SubscriptionRepository;
import com.NexDrive.tenantService.repository.TenantRepository;
import com.NexDrive.tenantService.service.TenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class TenantServiceImpl implements TenantService {

    private TenantRepository tenantRepository;
    private SubscriptionRepository subscriptionRepository;

    public TenantServiceImpl( TenantRepository tenantRepository , SubscriptionRepository subscriptionRepository)
    {
        this.tenantRepository = tenantRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @TrackExecutionTime(
            warnAfter = 2000,
            operationName = "creating new tenant"
    )
    @Override
    @Transactional
    public TenantCreationResponseDTO createTenant(
            TenantCreationRequestDTO tenantDTO,
            SubscriptionRequestDTO subscriptionDTO
    )
    {

        ZonedDateTime startDate = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));

        Subscription subscription = Subscription.builder()
                .status(SubscriptionStatus.ACTIVE)
                .plan(subscriptionDTO.getPlan())
                .startDate(startDate)
                .endDate(startDate.plusDays(30))
                .storageQuotaBytes(SubscriptionInfoHelper.getSubscriptionStorageQuotaBytes(subscriptionDTO.getPlan()))
                .seatLimit(SubscriptionInfoHelper.getSubscriptionSeatLimit(subscriptionDTO.getPlan()))
                .build();



        Tenant tenant = Tenant.builder()
                .name(tenantDTO.getName())
                .phone(tenantDTO.getPhone())
                .createdAt(ZonedDateTime.now())
                .status(TenantStatus.ACTIVE)
                .seatsLeft(SubscriptionInfoHelper.getSubscriptionSeatLimit(subscriptionDTO.getPlan()))
                .build();

        tenant.getSubscriptions().add(subscription);

        return TenantCreationResponseDTO.builder()
                .name(tenantDTO.getName())
                .build();

    }
}
