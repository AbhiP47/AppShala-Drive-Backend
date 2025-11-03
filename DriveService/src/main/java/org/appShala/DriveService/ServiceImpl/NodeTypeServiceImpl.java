package org.appShala.DriveService.ServiceImpl;

import org.appShala.DriveService.Model.NodeType;
import org.appShala.DriveService.Repository.NodeTypeRepository;
import org.appShala.DriveService.Service.NodeTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
@Service
public class NodeTypeServiceImpl implements NodeTypeService
{
    private final NodeTypeRepository nodeTypeRepository;
    public NodeTypeServiceImpl(NodeTypeRepository nodeTypeRepository){
        this.nodeTypeRepository = nodeTypeRepository;
    }

    @Override
    @Transactional
    public List<NodeType> getAllNodeTypes() {
        return nodeTypeRepository.findAll();
    }

    @Override
    @Transactional
    public NodeType getNodeTypeById(UUID id)
    {
        return nodeTypeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("NodeType metadata not found"));
    }

    @Override
    @Transactional
    public NodeType getByFileExtension(String extension)
    {
        return nodeTypeRepository.findByFileExtension(extension)
                .orElseThrow(()-> new RuntimeException("File Extension not found"));
    }
}
