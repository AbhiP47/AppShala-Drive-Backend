package com.DriveService.DriveService.ServiceImpl;

import com.DriveService.DriveService.Model.NodeType;
import com.DriveService.DriveService.Repository.NodeTypeRepository;
import com.DriveService.DriveService.Service.NodeTypeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
@Service
public class NodeTypeServiceImpl implements NodeTypeService
{
    private final NodeTypeRepository nodeTypeRepository;

    @Autowired
    public NodeTypeServiceImpl(NodeTypeRepository nodeTypeRepository) {
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
                .orElseThrow(() -> new NoSuchElementException("NodeType not found for ID: " + id));
    }

    @Override
    @Transactional
    public NodeType getByFileExtension(String extension) {
        return nodeTypeRepository.findByFileExtensionIgnoreCase(extension)
                .orElseThrow(() -> new NoSuchElementException("NodeType not found for extension: " + extension));
    }

    @Override
    @Transactional
    public NodeType createNodeType(NodeType nodeType) {
        return nodeTypeRepository.save(nodeType);
    }

    @Override
    @Transactional
    public NodeType updateNodeType(UUID id, NodeType nodeType) {
        NodeType existing = nodeTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("NodeType not found for ID: " + id));

        existing.setType(nodeType.getType());
        existing.setIsFolder(nodeType.getIsFolder());
        existing.setIcon(nodeType.getIcon());
        existing.setFileExtension(nodeType.getFileExtension());
        return nodeTypeRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteNodeType(UUID id) {
        NodeType existing = nodeTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("NodeType not found for ID: " + id));
        nodeTypeRepository.delete(existing);
    }
}
