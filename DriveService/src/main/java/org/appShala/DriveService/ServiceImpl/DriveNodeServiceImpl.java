package org.appShala.DriveService.ServiceImpl;

import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.appShala.DriveService.Model.DriveNode;
import org.appShala.DriveService.Payloads.DriveNodeCreationRequest;
import org.appShala.DriveService.Payloads.DriveNodeResponse;
import org.appShala.DriveService.Repository.DriveNodeRepository;
import org.appShala.DriveService.Service.DriveNodeService;
import org.appShala.DriveService.Service.NodeTypeService;
import org.appShala.DriveService.Service.SharedNodeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private DriveNodeResponse mapToResponse(DriveNode node) {
        // This mapping logic calculates isStarred/isShared status, requires external calls/joins
        DriveNodeResponse response = new DriveNodeResponse();
        response.setId(node.getId());
        response.setName(node.getName());
        response.setType(node.getType());
        response.setSizeBytes(node.getSizeBytes());
        response.setCreatedAt(node.getCreatedAt());
        response.setLastModifiedAt(node.getLastModifiedAt());
        return response;
    }
    @Override
    @Transactional
    public DriveNodeResponse createNode(DriveNodeCreationRequest Request , UUID UserId){
        List<DriveNode> exsitingNodes = DriveNodeRepository
                .findByNameAndParentId(request.getName() , request.getParentId());
        if (!exsitingNodes.isEmpty) throw new IllegalArgumentException("Node already exists");
    }
}
