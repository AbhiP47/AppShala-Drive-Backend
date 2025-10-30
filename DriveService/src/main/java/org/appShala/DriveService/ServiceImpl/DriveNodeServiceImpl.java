package org.appShala.DriveService.ServiceImpl;

import jakarta.transaction.Transactional;
import org.appShala.DriveService.Model.DriveNode;
import org.appShala.DriveService.Payloads.DriveNodeCreationRequest;
import org.appShala.DriveService.Payloads.DriveNodeResponse;
import org.appShala.DriveService.Repository.DriveNodeRepository;
import org.appShala.DriveService.Repository.StarredNodeRepository;
import org.appShala.DriveService.Service.DriveNodeService;
import org.appShala.DriveService.Service.NodeTypeService;
import org.appShala.DriveService.Service.SharedNodeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DriveNodeServiceImpl implements DriveNodeService{
    private final DriveNodeRepository driveNodeRepository;
    private final SharedNodeService sharedNodeService;
    private final NodeTypeService nodeTypeService;

    public DriveNodeServiceImpl(DriveNodeRepository driveNodeRepository,
                                SharedNodeService sharedNodeService,
                                NodeTypeService nodeTypeService) {
        this.driveNodeRepository = driveNodeRepository;
        this.sharedNodeService = sharedNodeService;
        this.nodeTypeService = nodeTypeService;
    }
private DriveNode maptoEntity(DriveNodeCreationRequest request , UUID UserId){
        DriveNode node = new DriveNode();
    node.setName(request.getName());
    node.setNodeType(request.getNodeType());
    node.setOwnerId(UserId);
    node.setModifiedByID(UserId);
    node.setIsDeleted(false);
    node.setCreatedAt(LocalDateTime.now());
    node.setLastModifiedAt(LocalDateTime.now());

    if(request.getParent_Id()!=null)
        driveNodeRepository.findById(request.getParent_Id.ifPresent(node::setParentNode))
    return node;
    }
    private DriveNodeResponse mapToResponse(DriveNode node, UUID requestingUserId) {
        DriveNodeResponse response = new DriveNodeResponse();
        response.setId(node.getId());
        response.setName(node.getName());
        response.setType(node.getType());
        response.setCreatedAt(node.getCreatedAt());
        boolean isStarred = StarredNodeRepository.findByDriveNodeIdAndStarredBy(node.getId(), requestingUserId).isPresent();
        response.setStarred(isStarred);

        return response;
    }
    @Override
    @Transactional
    public DriveNodeResponse createNode(DriveNodeCreationRequest Request , UUID UserId){
        List<DriveNode> exsitingNodes = DriveNodeRepository
                .findByNameAndParentId(Request.getName() , Request.getParentId());
        if (!exsitingNodes.isEmpty)
            throw new IllegalArgumentException("Node already exists");
        DriveNode nodeToSave = maptoEntity(Request, UserId);
        DriveNode savedNode = driveNodeRepository.save(nodeToSave);

        return mapToResponse(savedNode, requestingUserId);

    }
    @Override
    @Transactional
    public DriveNodeResponse getNodeDetails(UUID NodeId){
        UUID requestingUserId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        DriveNode node = driveNodeRepository.findById(NodeId).orElseThrow(() -> new RuntimeException("Node not found."));

        return mapToResponse(node, requestingUserId);
    }
    @Override
    @Transactional

}
