package com.DriveService.DriveService.Repository;

import com.DriveService.DriveService.Model.NodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NodeTypeRepository extends JpaRepository<NodeType , UUID>
{
        Optional<NodeType> findByFileExtensionIgnoreCase(String fileExtension);

}
