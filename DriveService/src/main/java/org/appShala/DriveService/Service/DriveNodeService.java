package org.appShala.DriveService.Service;

import org.appShala.DriveService.Payloads.DriveNodeCreationRequest;
import org.appShala.DriveService.Payloads.DriveNodeResponse;

import java.util.UUID;

public interface DriveNodeService {
    DriveNodeResponse createNode(DriveNodeCreationRequest request , UUID UserId);
    DriveNodeResponse getNodeDetails(UUID nodeId);
    DriveNodeResponse getFolderContent(UUID parentId);
    DriveNodeResponse updateNode(UUID nodeId ,  DriveNodeCreationRequest request , UUID UserId);

    void softDeleteNode(UUID nodeId);


}
