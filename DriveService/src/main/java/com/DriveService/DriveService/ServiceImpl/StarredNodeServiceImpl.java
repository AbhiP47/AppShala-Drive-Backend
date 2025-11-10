package com.DriveService.DriveService.ServiceImpl;

import com.DriveService.DriveService.Model.DriveNode;
import com.DriveService.DriveService.Model.StarredNode;
import com.DriveService.DriveService.Repository.DriveNodeRepository;
import com.DriveService.DriveService.Repository.StarredNodeRepository;
import com.DriveService.DriveService.payloads.StarredNodeResponse;
import com.DriveService.DriveService.Service.StarredNodeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class StarredNodeServiceImpl implements StarredNodeService {
    private final StarredNodeRepository starredNodeRepository;
    private final DriveNodeRepository driveNodeRepository;

    @Autowired
    public StarredNodeServiceImpl(StarredNodeRepository starredNodeRepository, DriveNodeRepository driveNodeRepository) {
        this.starredNodeRepository = starredNodeRepository;
        this.driveNodeRepository = driveNodeRepository;
    }

    @Override
    @Transactional
    public StarredNodeResponse toggleStarredStatus(UUID nodeId, Boolean isStarred, UUID UserId) {
        DriveNode node = driveNodeRepository.findById(nodeId)
                .orElseThrow(() -> new NoSuchElementException("DriveNode not found"));

        StarredNode starredNode = starredNodeRepository.findByDriveNodeIdAndStarredBy(nodeId, UserId)
                .orElseGet(() -> new StarredNode());
        starredNode.setDriveNode(node);
        starredNode.setStarredBy(UserId);
        starredNode.setIsStarred(isStarred != null ? isStarred : false);
        starredNode.setStarredAt(LocalDateTime.now());

        starredNodeRepository.save(starredNode);

        return new StarredNodeResponse(
                nodeId,
                UserId,
                starredNode.getIsStarred(),
                starredNode.getStarredAt()
        );
    }

    @Override
    @Transactional
    public List<StarredNodeResponse> getStarredNodesForUser(UUID UserId) {
        List<StarredNode> list = starredNodeRepository.findAllByStarredBy(UserId);
        return list.stream()
                .filter(StarredNode::getIsStarred)
                .map(node -> new StarredNodeResponse(
                        node.getDriveNode().getId(),
                        node.getStarredBy(),
                        node.getIsStarred(),
                        node.getStarredAt()
                ))
                .collect(Collectors.toList());
    }
}


