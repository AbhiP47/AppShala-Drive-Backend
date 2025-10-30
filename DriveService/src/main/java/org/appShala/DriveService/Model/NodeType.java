package org.appShala.DriveService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NodeType")
public class NodeType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    @Column(name="type" , nullable = false)
    private String type;

    @Column(name = "is_Folder" , nullable = false)
    private boolean isFolder;

    @Column(name = "icon")
    private String icon;

    @Column(name = "file_extension")
    private String fileExtension;
}
