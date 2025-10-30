package org.appShala.DriveService.Service;

import org.appShala.DriveService.Model.NodeType;
import java.util.List;
import java.util.UUID;

public interface NodeTypeService {

    List<NodeType> getAllNodeTypes();
    NodeType getNodeTypeById(UUID id);
    NodeType getByFileExtension(String extension);
}
