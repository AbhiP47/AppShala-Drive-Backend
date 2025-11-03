package org.appShala.DriveService.Repository;

import org.appShala.DriveService.Model.SharedNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SharedNodeRepository extends JpaRepository<SharedNode, UUID>
{
    SharedNode findByDriveNodeIdAndSharedWith(UUID nodeId, UUID sharedWithId);
    SharedNode findByDriveNodeIdAndStarredBy(UUID nodeId, UUID starredBy);
    SharedNode findUserPermissionForNode(UUID nodeId, UUID userId);

    List<SharedNode> findAllBySharedWith(UUID sharedWithId);
}
