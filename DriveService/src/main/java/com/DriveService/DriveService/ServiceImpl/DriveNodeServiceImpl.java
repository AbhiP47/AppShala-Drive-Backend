package com.DriveService.DriveService.ServiceImpl;

import com.DriveService.DriveService.Enum.Type;
import com.DriveService.DriveService.Model.*;
import com.DriveService.DriveService.payloads.*;
import com.DriveService.DriveService.Repository.*;
import com.DriveService.DriveService.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DriveNodeServiceImpl implements DriveNodeService {
    private final DriveNodeRepository driveNodeRepository;
    private final NodeTypeRepository nodeTypeRepository;
    private final StarredNodeRepository starredNodeRepository;

    private DriveNodeResponse mapToDriveNodeResponse(DriveNode node, Boolean isShared, Boolean isStarred) {
        return DriveNodeResponse.builder()
                .id(node.getId())
                .name(node.getName())
                .sizeBytes(node.getSizeBytes())
                .ownerId(node.getOwnerId())
                .nodeType(node.getNodeType().getId())
                .createdAt(node.getCreatedAt())
                .lastModifiedAt(node.getLastModifiedAt())
                .isShared(isShared)
                .isStarred(isStarred)
                .nodeType(node.getNodeType() != null ? node.getNodeType().getId() : null)
                .description(node.getDescription())
                .nodes(null)
                .totalNodes(null)
                .build();
    }

    @Autowired
    public DriveNodeServiceImpl(DriveNodeRepository driveNodeRepository, NodeTypeRepository nodeTypeRepository, StarredNodeRepository starredNodeRepository) {
        this.driveNodeRepository = driveNodeRepository;
        this.nodeTypeRepository = nodeTypeRepository;
        this.starredNodeRepository = starredNodeRepository;
    }

    @Override
    @Transactional
    public DriveNodeResponse createNode(DriveNodeCreationRequest request, UUID UserId) {
        NodeType nodeType = nodeTypeRepository.findById(request.getNodeType())
                .orElseThrow(() -> new NoSuchElementException("NodeType not found"));

        DriveNode parent = null;
        if (request.getParentId() != null) {
            parent = driveNodeRepository.findById(request.getParentId())
                    .orElseThrow(() -> new NoSuchElementException("Parent folder not found"));
        }

        DriveNode driveNode = DriveNode.builder()
                .name(request.getName())
                .type(nodeType.getIsFolder() ? Type.FOLDER : Type.FILE)
                .nodeType(nodeType)
                .parent(parent)
                .sizeBytes(request.getSizeBytes())
                .storageUrl(request.getStorageUrl() != null ? request.getStorageUrl() : null)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .ownerId(UserId)
                .description(request.getDescription())
                .isShared(false)
                .build();

        driveNodeRepository.save(driveNode);
        return mapToDriveNodeResponse(driveNode, false, false);
    }


    @Override
    @Transactional
    public DriveNodeResponse getNodeDetails(UUID nodeId) {
        DriveNode node = driveNodeRepository.findById(nodeId)
                .orElseThrow(() -> new NoSuchElementException("Node not found"));

        boolean isStarred = (boolean) starredNodeRepository
                .findByDriveNodeIdAndStarredBy(nodeId, node.getOwnerId())
                .map(StarredNode::isStarred)
                .orElse(false);


        boolean isShared = node.getIsShared() != null && node.getIsShared();

        return mapToDriveNodeResponse(node, isShared, isStarred);
    }


    @Override
    @Transactional
    public DriveNodeResponse getFolderContent(UUID parentId) {
        DriveNode parent = driveNodeRepository.findById(parentId)
                .orElseThrow(() -> new NoSuchElementException("Parent node not found"));

        List<DriveNode> children = driveNodeRepository.findAllByParent(parent);
        List<DriveNodeResponse> nodes = children.stream()
                .map(child -> {
                    boolean isShared = child.getIsShared() != null && child.getIsShared();
                    boolean isStarred = (boolean) starredNodeRepository.findByDriveNodeIdAndStarredBy(child.getId(), child.getOwnerId())
                            .map(StarredNode::isStarred)
                            .orElse(false);
                    return mapToDriveNodeResponse(child, isShared, isStarred);
                })
                .collect(Collectors.toList());

        return DriveNodeResponse.builder()
                .id(parent.getId())
                .name(parent.getName())
                .nodes(nodes)
                .totalNodes(nodes.size())
                .build();
    }


    @Override
    @Transactional
    public DriveNodeResponse updateNode(UUID nodeId, DriveNodeUpdateRequest request, UUID UserId) {
        DriveNode node = driveNodeRepository.findById(nodeId)
                .orElseThrow(() -> new NoSuchElementException("Node not found"));

        if (request.getName() != null && !request.getName().isBlank()) {
            node.setName(request.getName());
        }
        if (request.getDescription() != null) {
            node.setDescription(request.getDescription());
        }
        if (request.getParentId() != null) {
            DriveNode parent = driveNodeRepository.findById(request.getParentId())
                    .orElseThrow(() -> new NoSuchElementException("Parent folder not found"));
            node.setParent(parent);
        }
        if (request.getNodeType() != null) {
            NodeType nodeType = nodeTypeRepository.findById(request.getNodeType())
                    .orElseThrow(() -> new NoSuchElementException("NodeType not found"));
            new NodeType(nodeType.getId());

        }
        if (request.getIsShared() != null) {
            node.setIsShared(request.getIsShared());
        }
        node.setLastModifiedAt(LocalDateTime.now());
        node.setModifiedByID(UserId);

        driveNodeRepository.save(node);

        boolean isStarred = (boolean) starredNodeRepository.findByDriveNodeIdAndStarredBy(node.getId(), node.getOwnerId())
                .map(StarredNode::isStarred).orElse(false);

        boolean isShared = node.getIsShared() != null && node.getIsShared();

        return mapToDriveNodeResponse(node, isShared, isStarred);
    }

    @Override
    @Transactional
    public void softDeleteNode(UUID nodeId) {
        DriveNode node = driveNodeRepository.findById(nodeId)
                .orElseThrow(() -> new NoSuchElementException("Node not found"));

        node.setDeletedAt(LocalDateTime.now());
        driveNodeRepository.save(node);
    }

    @Override
    @Transactional
    public boolean parentExists(UUID parentId) {
        if (parentId == null) return false;
        return driveNodeRepository.findById(parentId).isPresent();
    }
}

