package org.appShala.DriveService.Model;

import lombok.Builder;
import org.appShala.DriveService.Model.DriveNode;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "node_type")
    private UUID nodeType;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "parent_id" , referencedColumnName = "id")
    private DriveNode parentNode;

    @Column(name="storage_id")
    private UUID storageId;

    @Column(name= "size_bytes")
    private Long sizeBytes;

    @Column(name="created_at" , nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_modified_at" , nullable = false)
    private LocalDateTime lastModifiedAt;

    @Column(name = "shared_with")
    private Boolean sharedWith;

    @Column(name = "description")
    private String description;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Column(name= "modified_by")
    private UUID modifiedByID;

}
