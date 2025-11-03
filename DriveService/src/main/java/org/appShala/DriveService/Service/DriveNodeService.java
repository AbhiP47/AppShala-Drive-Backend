package org.appShala.DriveService.Service;

import jakarta.validation.Valid;
import org.appShala.DriveService.Payloads.DriveNodeCreationRequest;
import org.appShala.DriveService.Payloads.DriveNodeResponse;
import org.appShala.DriveService.Payloads.DriveNodeUpdateRequest;

import java.util.UUID;

public interface DriveNodeService {
    DriveNodeResponse createNode(DriveNodeCreationRequest request , UUID UserId);
    DriveNodeResponse getNodeDetails(UUID nodeId);
    DriveNodeResponse getFolderContent(UUID parentId);
    DriveNodeResponse updateNode(UUID nodeId , @Valid DriveNodeUpdateRequest request , UUID UserId);

    void softDeleteNode(UUID nodeId);
}
