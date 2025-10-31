package org.appShala.DriveService.Repository;

import org.appShala.DriveService.Model.DriveNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DriveNodeRepository extends JpaRepository<DriveNode , UUID>
{
    List<DriveNode> findAllByParentId(UUID ParentId);
    List<DriveNode> findByNameAndParentId(String name, UUID ParentId);
    List<DriveNode> findAllByOwnerId(UUID OwnerId);
    List<DriveNode> findAllByIsDeletedTrue();
}
