package com.NexDrive.tenantService.repository;

import com.NexDrive.tenantService.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription , UUID> {
}
