package org.appShala.DriveService.Service;

import org.appShala.DriveService.Payloads.StarredNodeRequest;
import org.appShala.DriveService.Payloads.StarredNodeResponse;

import java.util.List;
import java.util.UUID;

public interface StarredNodeService {
    StarredNodeResponse toggleStarredStatus(UUID nodeId , Boolean isStarred , UUID UserId);
    List<StarredNodeResponse>getStarredNodesForUser(UUID UserId);
}
