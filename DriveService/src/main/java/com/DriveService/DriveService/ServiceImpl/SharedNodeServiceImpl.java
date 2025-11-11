package com.DriveService.DriveService.ServiceImpl;

import com.DriveService.DriveService.Model.DriveNode;
import com.DriveService.DriveService.Model.SharedNode;
import com.DriveService.DriveService.payloads.SharedNodeRequest;
import com.DriveService.DriveService.payloads.SharedNodeResponse;
import com.DriveService.DriveService.Repository.DriveNodeRepository;
import com.DriveService.DriveService.Repository.SharedNodeRepository;
import com.DriveService.DriveService.Service.SharedNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.DriveService.DriveService.Enum.SharedWithEntity;
import com.DriveService.DriveService.Enum.Permission;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SharedNodeServiceImpl implements SharedNodeService {
    private final SharedNodeRepository sharedNodeRepository;
    private final DriveNodeRepository driveNodeRepository;

    @Autowired
    public SharedNodeServiceImpl(SharedNodeRepository sharedNodeRepository, DriveNodeRepository driveNodeRepository) {
        this.sharedNodeRepository = sharedNodeRepository;
        this.driveNodeRepository = driveNodeRepository;
    }

    private SharedNodeResponse mapToResponse(SharedNode node, String status) {
        return new SharedNodeResponse(
                node.getSharedWithEntity() != null ? node.getSharedWithEntity().name() : null,
                node.getPermission() != null ? node.getPermission().name() : null, status,
                node.getDriveNode() != null ? node.getDriveNode().getId() : null,
                node.getSharedWithId(), null, null,
                node.getSharedBy(),
                node.getSharedAt()
        );
    }
    @Override
    @Transactional
    public SharedNodeResponse shareNode(UUID nodeId, SharedNodeRequest request, UUID sharingUserId) {
        DriveNode node = driveNodeRepository.findById(nodeId)
                .orElseThrow(() -> new NoSuchElementException("DriveNode not found"));

        SharedNode sharedNode = SharedNode.builder()
                .driveNode(node)
                .sharedWithId(request.getSharedWithId())
                .sharedWithEntity(SharedWithEntity.valueOf(request.getSharedWithEntity()))
                .permission(Permission.valueOf(request.getPermission()))
                .sharedAt(LocalDateTime.now())
                .revoked(false)
                .sharedBy(sharingUserId)
                .isShared(true)
                .build();

        sharedNodeRepository.save(sharedNode);
        return mapToResponse(sharedNode, "SHARED");
    }

    @Override
    @Transactional
    public void revokePermission(UUID nodeId, UUID sharedWithId, UUID revokingUserId) {
        SharedNode sharedNode = sharedNodeRepository.findByDriveNodeIdAndSharedWithId(nodeId, sharedWithId);
        if (sharedNode == null) {
            throw new NoSuchElementException("SharedNode not found for node and user/group");
        }
        sharedNode.setRevoked(true);
        sharedNode.setIsShared(false);
        sharedNodeRepository.save(sharedNode);
    }


    @Override
    @Transactional
    public List<SharedNodeResponse> getNodesSharedWithMe(UUID userId) {
        List<SharedNode> sharedList = sharedNodeRepository.findAllBySharedWithId(userId);
        return sharedList.stream()
                .filter(node -> !node.getRevoked())
                .map(node -> mapToResponse(node, "ACTIVE"))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean hasPermission(UUID nodeId, UUID userId, String requiredPermission) {
        SharedNode sharedNode = sharedNodeRepository.findByDriveNodeIdAndSharedWithId(nodeId, userId);
        if (sharedNode == null || sharedNode.getRevoked()) return false;
        Permission perm = Permission.valueOf(requiredPermission);
        return sharedNode.getPermission() == perm;
    }
}
