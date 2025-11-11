package com.DriveService.DriveService.Service;

import com.DriveService.DriveService.Model.NodeType;
import java.util.List;
import java.util.UUID;

public interface NodeTypeService {

    List<NodeType> getAllNodeTypes();
    NodeType getNodeTypeById(UUID id);
    NodeType getByFileExtension(String extension);
    NodeType createNodeType(NodeType nodeType);
    NodeType updateNodeType(UUID id, NodeType nodeType);
    void deleteNodeType(UUID id);

}
