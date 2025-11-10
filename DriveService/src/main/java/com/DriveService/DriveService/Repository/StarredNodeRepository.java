package com.DriveService.DriveService.Repository;

import com.DriveService.DriveService.Model.StarredNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StarredNodeRepository extends JpaRepository<StarredNode , UUID>
{
    List<StarredNode> findAllByStarredBy(UUID starredByUserId);
    Optional<StarredNode> findByDriveNodeIdAndStarredBy(UUID nodeId, UUID userId);

}
