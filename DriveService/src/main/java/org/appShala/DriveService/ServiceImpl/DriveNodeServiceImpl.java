package org.appShala.DriveService.ServiceImpl;

import org.appShala.DriveService.Model.*;
import org.appShala.DriveService.Payloads.*;
import org.appShala.DriveService.Repository.*;
import org.appShala.DriveService.Service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DriveNodeServiceImpl implements DriveNodeService {
    private final DriveNodeRepository driveNodeRepository;
    private final SharedNodeService sharedNodeService;
    private final NodeTypeService nodeTypeService;
    private final StarredNodeService starredNodeService;

    public DriveNodeServiceImpl(DriveNodeRepository driveNodeRepository,
                                SharedNodeService sharedNodeService,
                                NodeTypeService nodeTypeService, StarredNodeService starredNodeService) {
        this.driveNodeRepository = driveNodeRepository;
        this.sharedNodeService = sharedNodeService;
        this.nodeTypeService = nodeTypeService;
        this.starredNodeService = starredNodeService;
    }

    private DriveNode maptoEntity( DriveNodeCreationRequest request, UUID UserId) {
        DriveNode node = new DriveNode();
        node.setName(request.getName());
        node.setNodeType(request.getNodeType());
        node.setOwnerId(UserId);
        node.setModifiedByID(UserId);
        node.setIsDeleted(false);
        node.setCreatedAt(LocalDateTime.now());
        node.setLastModifiedAt(LocalDateTime.now());

        if (request.getParentId() != null) {
            driveNodeRepository.findById(request.getParent())
                    .ifPresent(node::setParentNode);
        }
        return node;
    }

    private DriveNodeResponse mapToResponse(DriveNode node, UUID requestingUserId) {
        DriveNodeResponse response = new DriveNodeResponse();
        response.setId(node.getId());
        response.setName(node.getName());
        response.setType(node.getType());
        response.setCreatedAt(node.getCreatedAt());
        boolean isStarred = SharedNodeRepository.findByDriveNodeIdAndStarredBy(node.getId(), requestingUserId).isPresent();
        response.setStarred(isStarred);

        return response;
    }

    @Override
    @Transactional
    public DriveNodeResponse createNode(DriveNodeCreationRequest request, UUID UserId)
    {
        List<DriveNode> existingNodes = driveNodeRepository
                .findByNameAndParentId(request.getName , request.getParentId());
        if (!existingNodes.isEmpty()) {
            throw new IllegalArgumentException("A file or folder with this name already exists in this location.");
        }
        DriveNode nodeToSave = maptoEntity(request, UserId);
        DriveNode savedNode = driveNodeRepository.save(nodeToSave);
        return mapToResponse(savedNode, UserId);
    }

    @Override
    @Transactional
    public DriveNodeResponse getNodeDetails(UUID nodeId)
    {
        UUID requestingUserid = UUID.fromString(nodeId.toString());
        DriveNode node = driveNodeRepository.findById(nodeId)
                .orElseThrow(() -> new RuntimeException("Node not found."));
        return mapToResponse(node , requestingUserid);
    }

    @Override
    @Transactional
    public DriveNodeResponse getFolderContent(UUID parentId)
    {
        UUID requestingUserid = UUID.fromString(parentId.toString());
        List<DriveNode> children = driveNodeRepository.findAllByParentId(parentId);
        List<DriveNodeResponse> responses = children.stream()
                .map(node-> mapToResponse(node, requestingUserid))
                .collect(Collectors.toList());
        DriveNodeListResponse ListResponse = new DriveNodeListResponse();
        ListResponse.setNodes(responses);
        ListResponse.setTotalNodes(responses.size());
        return ListResponse;
    }

    @Override
    @Transactional
    public DriveNodeResponse updateNode(UUID nodeId, DriveNodeCreationRequest request, UUID UserId)
    {
        DriveNode node =driveNodeRepository.findById(nodeId)
                .orElseThrow(() -> new RuntimeException("Node not found."));
        if (request.getName()!=null)
        {
            node.setName(request.getName());
        }
        node.setLastModifiedAt(LocalDateTime.now());
        DriveNode updatedNode = driveNodeRepository.save(node);
        return mapToResponse(updatedNode, UserId);
    }

    @Override
    @Transactional
    public void softDeleteNode(UUID nodeId)
    {
        UUID modifyingUserId = UUID.fromString(nodeId.toString());
        DriveNode node = driveNodeRepository.findById(nodeId)
                .orElseThrow(() -> new RuntimeException("Node not found."));
        node.setIsDeleted(true);
        node.setDeletedAt(LocalDateTime.now());
        // node.setModifiedByID(modifyingUserId);
        node.setLastModifiedAt(LocalDateTime.now());

        driveNodeRepository.save(node);

    }
}

