package com.DriveService.DriveService.Model;

import com.DriveService.DriveService.Enum.Type;
import lombok.Builder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "drive_node")
public class DriveNode {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "node_type", referencedColumnName = "id")
    private NodeType nodeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id" , nullable = false)
    private DriveNode parent;

    @Column(name="storage_url")
    private String storageUrl;

    @Column(name= "size_bytes")
    private Long sizeBytes;

    @Column(name="created_at" , nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_modified_at" , nullable = false)
    private LocalDateTime lastModifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_with")
    private SharedNode sharedWith;

    @Column(name = "description")
    private String description;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Column(name= "modified_by")
    private UUID modifiedByID;

    @Column(name="is_shared")
    private Boolean isShared;
}
