package com.DriveService.DriveService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="starred_node")
public class StarredNode {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private DriveNode driveNode;

    @Column(name = "starred_by", nullable = false)
    private UUID starredBy;

    @Column(name = "starred_at", nullable = false)
    private LocalDateTime starredAt;

    @Column(name = "is_starred", nullable = false)
    private Boolean isStarred;

    public Object isStarred()
    {
        return isStarred;
    }
}

