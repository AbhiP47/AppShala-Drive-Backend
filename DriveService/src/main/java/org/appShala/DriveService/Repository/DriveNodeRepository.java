package org.appShala.DriveService.Repository;

import org.appShala.DriveService.Model.DriveNode;
import org.appShala.DriveService.Model.SharedNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DriveNodeRepository extends JpaRepository<DriveNode> {

}
