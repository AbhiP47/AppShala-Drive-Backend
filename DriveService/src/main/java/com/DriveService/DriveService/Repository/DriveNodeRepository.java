package com.DriveService.DriveService.Repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import com.DriveService.DriveService.Model.DriveNode;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface DriveNodeRepository extends JpaRepository<DriveNode, UUID> {

    List<DriveNode> findAllByOwnerId(UUID ownerId);
    Optional<DriveNode> findById(UUID id);
    List<DriveNode> findByNameAndParent(String name, DriveNode parent);
    List<DriveNode> findAllByParent(DriveNode parent);

}


