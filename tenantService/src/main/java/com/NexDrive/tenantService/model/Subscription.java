package com.NexDrive.tenantService.model;

import com.NexDrive.tenantService.enums.SubscriptionPlanTier;
import com.NexDrive.tenantService.enums.SubscriptionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_plan_tier" , nullable = false)
    private SubscriptionPlanTier plan;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_status" , nullable = false)
    private SubscriptionStatus status;

    @PositiveOrZero
    @Column(name = "storage_quota_bytes", nullable = false)
    private long storageQuotaBytes;

    @Min(1)
    @Column(name = "seat_limit", nullable = false)
    private int seatLimit;

    @Column(name = "plan_cancellation_reason")
    private String cancellationReason;

    @CreationTimestamp
    @Column(name="start_date" , nullable = false)
    private ZonedDateTime startDate;

    @Column(name = "end_date" , nullable = false)
    private ZonedDateTime endDate;

}
