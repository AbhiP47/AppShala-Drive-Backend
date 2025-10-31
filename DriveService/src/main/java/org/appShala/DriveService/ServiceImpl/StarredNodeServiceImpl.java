package org.appShala.DriveService.ServiceImpl;

import org.appShala.DriveService.Payloads.StarredNodeResponse;
import org.appShala.DriveService.Service.StarredNodeService;

import java.util.List;
import java.util.UUID;

public class StarredNodeServiceImpl implements StarredNodeService {

    @Override
    public StarredNodeResponse toggleStarredStatus(UUID nodeId, Boolean isStarred, UUID UserId) {
        return null;
    }

    @Override
    public List<StarredNodeResponse> getStarredNodesForUser(UUID UserId) {
        return List.of();
    }
}
