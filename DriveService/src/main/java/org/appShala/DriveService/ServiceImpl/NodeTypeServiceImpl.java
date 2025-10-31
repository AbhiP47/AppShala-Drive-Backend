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
    public NodeTypeServiceImpl(NodeTypeRepository nodeTypeRepository){
    }

    @Override
    @Transactional
    public List<NodeType> getAllNodeTypes() {
        return NodeTypeRepository.findAll();
    }

    @Override
    @Transactional
    public NodeType getNodeTypeById(UUID id)
    {
        return NodeTypeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("NodeType metadata not found"));
    }

    @Override
    @Transactional
    public NodeType getByFileExtension(String extension)
    {
        return NodeTypeRepository.findByFileExtension(extension)
                .orElseThrow(()-> new RuntimeException("File Extension not found"));
    }
}
