package com.NexDrive.tenantService.service;

import com.NexDrive.tenantService.model.Subscription;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface SubscriptionService {

    public Subscription createSubscriptionForNewTenant(UUID tenantId);
}
