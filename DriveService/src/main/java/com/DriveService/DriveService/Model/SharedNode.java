package com.DriveService.DriveService.Model;

import com.DriveService.DriveService.Enum.Permission;
import com.DriveService.DriveService.Enum.SharedWithEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shared_node")
public class SharedNode {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private DriveNode driveNode;

    @Column(name = "shared_with", nullable = false)
    private UUID sharedWithId;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(
            write = "?::sharedWithEntity",
            read = "sharedWithEntity::text"
    )
    private SharedWithEntity sharedWithEntity;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(
            write = "?::permission",
            read="permission::text"
    )
    private Permission permission;

    @Column(name = "shared_at", nullable = false)
    private LocalDateTime sharedAt;

    @Column(name = "revoked", nullable = false)
    private Boolean revoked;

    @Column(name = "shared_by", nullable = false)
    private UUID sharedBy;

    @Column(name = "is_shared", nullable = false)
    private Boolean isShared;
}
