package org.appShala.DriveService.Repository;

import org.appShala.DriveService.Model.StarredNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StarredNodeRepository extends JpaRepository<StarredNode , UUID>
{
    List<StarredNode> findAllByStarredBy(UUID starredByUserId);
    Optional<StarredNode> findByDriveNodeIdAndStarredBy(UUID nodeId, UUID userId);


}
