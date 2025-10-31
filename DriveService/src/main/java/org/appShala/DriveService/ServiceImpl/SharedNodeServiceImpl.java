package org.appShala.DriveService.ServiceImpl;

import org.appShala.DriveService.Model.SharedNode;
import org.appShala.DriveService.Payloads.SharedNodeRequest;
import org.appShala.DriveService.Payloads.SharedNodeResponse;
import org.appShala.DriveService.Repository.DriveNodeRepository;
import org.appShala.DriveService.Repository.SharedNodeRepository;
import org.appShala.DriveService.Service.SharedNodeService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SharedNodeServiceImpl implements SharedNodeService
{
    private final SharedNodeRepository sharedNodeRepository;
    private final DriveNodeRepository driveNodeRepository;
    public SharedNodeServiceImpl(SharedNodeService sharedNodeService , DriveNodeRepository driveNodeRepository)
    {
        this.sharedNodeRepository = sharedNodeRepository;
        this.driveNodeRepository = driveNodeRepository;
    }
    private SharedNodeResponse mapToResponse(SharedNode sharedNode)
    {
        SharedNodeResponse response = new SharedNodeResponse();
        response.setSharedWithId(sharedNode.getSharedWith());
        response.setPermission(sharedNode.getPermission());
        response.setSharedAt(sharedNode.getSharedAt());
        return response;
    }

    @Override
    @Transactional
    public SharedNodeResponse shareNode(UUID nodeId, SharedNodeRequest request, UUID sharingUserId)
    {
        SharedNode shareToSave = SharedNodeRepository.findByDriveNodeIdAndSharedWith(nodeId , request.getSharedWithId());
        if(shareToSave == null)
        {
            shareToSave = new SharedNode();
            driveNodeRepository.findById(nodeId).ifPresent(shareToSave::setDriveNode);
            shareToSave.setSharedWithEntity(request.getSharedWithEntity());
            shareToSave.setSharedAt(LocalDateTime.now());
            shareToSave.setSharedWith(request.getSharedWith);
        }
        shareToSave.setPermission(request.getPermission());
        shareToSave.setSharedBy(sharingUserId);
        shareToSave.setRevoked(false);

        SharedNode savedShare = sharedNodeRepository.save(shareToSave);

        return mapToResponse(savedShare);
    }

    @Override
    @Transactional
    public void revokePermission(UUID nodeId, UUID sharedWithId, UUID revokingUserId)
    {
        SharedNode share = SharedNodeRepository.findByDriveNodeidAdSharedId(nodeId , sharedWithId);
        if(share != null)
        {
           return;
        }
        share.setRevoked(true);
        sharedNodeRepository.save(share);
    }

    @Override
    @Transactional
    public List<SharedNodeResponse> getNodesSharedWithMe(UUID userId)
    {
        List<SharedNode> sharedNodes = sharedNodeRepository.findAllBySharedWith(userId).stream()
                .filter(share -> !share.getRevoked()) // Only include active shares
                .collect(Collectors.toList());

        return sharedNodes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean hasPermission(UUID nodeId, UUID userId, String requiredPermission)
    {
        driveNodeRepository.findById(nodeId).ifPresent(node ->
        {
            if (node.getOwnerId().equals(userId))
                return true;
        });
        SharedNode share = sharedNodeRepository.findUserPermissionForNode(nodeId, userId).orElse(null);
        if (share != null && !share.getRevoked()) {
        }
        return true;
    }
}
