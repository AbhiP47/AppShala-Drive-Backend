package org.appShala.DriveService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Permission;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "sharred_node")
public class SharedNode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private DriveNode driveNode;

    @Column(name = "shared_with" , nullable = false)
    private UUID sharedWithId;

    @Enumerated(EnumType.STRING)
    @Column(name = "shared_with_entity", nullable = false)
    private String sharedWithEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission" , nullable = false)
    private String permission;

    @Column(name = "shared_at" , nullable = false)
    private LocalDateTime sharedAt;

    @Column(name="revoked" , nullable = false)
    private Boolean revoked;

    @Column(name = "shared_by", nullable = false)
    private UUID sharedBy;

    @Column(name="is_shared" , nullable= false)
    private Boolean isShared;
}
