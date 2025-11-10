package com.DriveService.DriveService.Service;

import com.DriveService.DriveService.payloads.StarredNodeResponse;

import java.util.List;
import java.util.UUID;

public interface StarredNodeService {
    StarredNodeResponse toggleStarredStatus(UUID nodeId , Boolean isStarred , UUID UserId);
    List<StarredNodeResponse>getStarredNodesForUser(UUID UserId);
}
