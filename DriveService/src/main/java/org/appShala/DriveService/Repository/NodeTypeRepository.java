package org.appShala.DriveService.Repository;

import org.appShala.DriveService.Model.NodeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NodeTypeRepository extends JpaRepository<NodeType , UUID>


{
}
