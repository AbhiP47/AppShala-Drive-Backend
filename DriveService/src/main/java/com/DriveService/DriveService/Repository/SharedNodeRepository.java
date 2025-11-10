package com.DriveService.DriveService.Repository;

import com.DriveService.DriveService.Model.SharedNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SharedNodeRepository extends JpaRepository<SharedNode, UUID>
{
    SharedNode findByDriveNodeIdAndSharedWithId(UUID nodeId, UUID sharedWithId);
    SharedNode findByDriveNodeIdAndSharedBy(UUID nodeId, UUID sharedBy);
    List<SharedNode> findAllBySharedWithId(UUID sharedWithId);
}
