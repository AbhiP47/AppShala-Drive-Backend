package org.appShala.DriveService.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "node_type")
public class NodeType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    @Column(name="type" , nullable = false)
    private String type;

    @Column(name = "is_folder" , nullable = false)
    private boolean isFolder;

    @Column(name = "icon")
    private String icon;

    @Column(name = "file_extension")
    private String fileExtension;
}
