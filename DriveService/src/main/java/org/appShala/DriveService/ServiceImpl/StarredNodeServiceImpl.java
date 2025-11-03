package org.appShala.DriveService.ServiceImpl;

import org.appShala.DriveService.Model.DriveNode;
import org.appShala.DriveService.Model.StarredNode;
import org.appShala.DriveService.Payloads.StarredNodeResponse;
import org.appShala.DriveService.Repository.DriveNodeRepository;
import org.appShala.DriveService.Repository.StarredNodeRepository;
import org.appShala.DriveService.Service.StarredNodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StarredNodeServiceImpl implements StarredNodeService {
    private final StarredNodeRepository starredNodeRepository;
    private final DriveNodeRepository driveNodeRepository;
    public StarredNodeServiceImpl(StarredNodeRepository starredNodeRepository, DriveNodeRepository driveNodeRepository){
        this.starredNodeRepository = starredNodeRepository;
        this.driveNodeRepository = driveNodeRepository;
    }
    private StarredNodeResponse mapToResponse(StarredNode starredNode)
    {
        StarredNodeResponse response =new StarredNodeResponse();
        response.setStarredAt(starredNode.getStarredAt());
        response.setIsStarred(starredNode.isStarred());
        response.setStarredBy(starredNode.getStarredBy());
        return response;
    }

    @Override
    @Transactional
    public StarredNodeResponse toggleStarredStatus(UUID nodeId, Boolean isStarred, UUID UserId)
    {
        DriveNode driveNode = driveNodeRepository.findById(nodeId)
                .orElseThrow(() -> new RuntimeException("DriveNode not found with ID: " + nodeId));

        Optional<StarredNode> optionalStar = starredNodeRepository.findByDriveNodeIdAndStarredBy(nodeId, UserId);
        StarredNode star = new StarredNode();
        if (optionalStar.isPresent()) {
            // Case 1: Record exists - update the status
            star = optionalStar.get();
        } else if (isStarred) {
            // Case 2: Record doesn't exist, but user wants to star it - create new record
            star = new StarredNode();
            star.setStarredBy(UserId);
            star.setDriveNode(driveNode);
            star.setStarredAt(LocalDateTime.now());
        } else {
            return new StarredNodeResponse();
        }
        star.setStarred(isStarred);
        StarredNode savedStar = starredNodeRepository.save(star);
        return mapToResponse(savedStar);
    }
    @Override
    @Transactional
    public List<StarredNodeResponse> getStarredNodesForUser(UUID UserId)
    {
        List<StarredNode> starredNodes=starredNodeRepository.findAllByStarredBy(UserId).stream()
                .filter(StarredNode::isStarred)
                .toList();
        return starredNodes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
