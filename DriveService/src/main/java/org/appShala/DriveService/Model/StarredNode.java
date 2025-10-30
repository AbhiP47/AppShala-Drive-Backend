package org.appShala.DriveService.Model;

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
@Table(name="Starred_Node")
public class StarredNode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private DriveNode driveNode;

    @Column(name="starredBy" , nullable = false)
    private UUID starredBy;

    @Column(name="Starred_At" , nullable = false)
    private LocalDateTime starredAt;

    @Column(name="is_Starred" , nullable = false)
    private boolean isStarred;
}
