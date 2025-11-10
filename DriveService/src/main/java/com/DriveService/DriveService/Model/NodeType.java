package com.DriveService.DriveService.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "node_type")
public class NodeType {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name="type" , nullable = false)
    private String type;

    @Column(name = "is_folder", nullable = false)
    private Boolean isFolder;

    @Column(name = "icon")
    private String icon;

    @Column(name = "file_extension")
    private String fileExtension;

    @OneToMany(mappedBy = "nodeType")
    private List<DriveNode> driveNodes;

    public NodeType(UUID id) {
        this.id = id;
    }
}

