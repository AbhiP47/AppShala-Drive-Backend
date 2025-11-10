package com.DriveService.DriveService.Service;

import com.DriveService.DriveService.payloads.SharedNodeRequest;
import com.DriveService.DriveService.payloads.SharedNodeResponse;

import java.util.List;
import java.util.UUID;

public interface SharedNodeService {
    SharedNodeResponse shareNode(UUID nodeId, SharedNodeRequest request, UUID sharingUserId);
    void revokePermission(UUID nodeId, UUID sharedWithId, UUID revokingUserId);
    List<SharedNodeResponse> getNodesSharedWithMe(UUID userId);
    boolean hasPermission(UUID nodeId, UUID userId, String requiredPermission);
}
