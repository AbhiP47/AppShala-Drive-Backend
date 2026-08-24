package com.NexDrive.tenantService.model;

import com.NexDrive.tenantService.enums.TenantStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tenants")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @NotNull
    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "phone_number" , nullable = false)
    private String phone;


    // Maintaining subscription history
    @OneToMany(
            mappedBy = "tenant",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OrderBy("startDate DESC")
    private List<Subscription> subscriptions = new ArrayList<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private TenantStatus status;

    @PositiveOrZero
    @Builder.Default
    @Column(name = "storage_used_bytes", nullable = false)
    private long storageUsedBytes = 0L;

    @Column(name = "remaining_seats", nullable = false)
    private int seatsLeft;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private ZonedDateTime createdAt;
}
