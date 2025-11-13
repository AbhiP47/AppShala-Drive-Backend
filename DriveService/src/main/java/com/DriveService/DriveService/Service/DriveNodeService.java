package com.DriveService.DriveService.Service;

import jakarta.validation.Valid;
import com.DriveService.DriveService.payloads.DriveNodeCreationRequest;
import com.DriveService.DriveService.payloads.DriveNodeResponse;
import com.DriveService.DriveService.payloads.DriveNodeUpdateRequest;

import java.util.UUID;

public interface DriveNodeService {
    DriveNodeResponse createNode(DriveNodeCreationRequest request , UUID UserId);
    DriveNodeResponse getNodeDetails(UUID nodeId);
    DriveNodeResponse getFolderContent(UUID parentId);
    DriveNodeResponse updateNode(UUID nodeId , @Valid DriveNodeUpdateRequest request , UUID UserId);
    void softDeleteNode(UUID nodeId);
    boolean parentExists(UUID parentId);
}


