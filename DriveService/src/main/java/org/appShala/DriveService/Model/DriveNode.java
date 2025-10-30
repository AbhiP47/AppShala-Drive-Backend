package org.appShala.DriveService.Model;

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
@Table(name = "DriveNode")
public class DriveNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "NodeType")
    private UUID nodeType;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "parentId" , referencedColumnName = "id")
    private DriveNode parentNode;

    @Column(name="StorageId")
    private UUID storageId;

    @Column(name= "sizeBytes")
    private Long sizeBytes;

    @Column(name="created_at" , nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "lastModifiedAt" , nullable = false)
    private LocalDateTime lastModifiedAt;

    @Column(name = "Shared_With")
    private Boolean sharedWith;

    @Column(name = "description")
    private String description;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "ownerId")
    private UUID ownerId;

    @Column(name= "modifiedBy")
    private UUID modifiedByID;

}
