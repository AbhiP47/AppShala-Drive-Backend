package com.DriveService.DriveService.Controller;

import com.DriveService.DriveService.Service.DriveNodeService;
import com.DriveService.DriveService.Service.StarredNodeService;
import com.DriveService.DriveService.payloads.DriveNodeCreationRequest;
import com.DriveService.DriveService.payloads.DriveNodeResponse;
import com.DriveService.DriveService.payloads.DriveNodeUpdateRequest;
import com.DriveService.DriveService.payloads.StarredNodeResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import  java.util.stream.IntStream;

@RestController
@RequestMapping("/api/drive/nodes")
public class DriveNodeController {
    private final DriveNodeService driveNodeService;
    private final StarredNodeService starredNodeService;

    public DriveNodeController(DriveNodeService driveNodeService, StarredNodeService starredNodeService) {
        this.driveNodeService = driveNodeService;
        this.starredNodeService = starredNodeService;
    }

    private UUID getMockUserId() {
        return UUID.fromString("00000000-0000-0000-0000-000000000001");
    }

    @PostMapping
    public ResponseEntity<?> createNode(
            @Valid @RequestBody DriveNodeCreationRequest request) {
        UUID userId = getMockUserId();
        if (request.getParentId() != null && !driveNodeService.parentExists(request.getParentId())) {
            return ResponseEntity.badRequest().body("Parent node does not exist");
        }
        DriveNodeResponse response = driveNodeService.createNode(request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{nodeId}")
    public ResponseEntity<DriveNodeResponse> getNodeDetails(@PathVariable UUID nodeId) {
        DriveNodeResponse response = driveNodeService.getNodeDetails(nodeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/content/{parentId}")
    public ResponseEntity<DriveNodeResponse> getFolderContent(@PathVariable UUID parentId) {
        DriveNodeResponse response = driveNodeService.getFolderContent(parentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{nodeId}")
    public ResponseEntity<DriveNodeResponse> updateNode(
            @PathVariable UUID nodeId,
            @Valid @RequestBody DriveNodeUpdateRequest request) {
        UUID userId = getMockUserId();
        DriveNodeResponse response = driveNodeService.updateNode(nodeId, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{nodeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteNode(@PathVariable UUID nodeId) {
        driveNodeService.softDeleteNode(nodeId);
    }

    @PutMapping("/{nodeId}/star")
    public ResponseEntity<StarredNodeResponse> toggleStar(
            @PathVariable UUID nodeId,
            @RequestParam boolean isStarred) {
        UUID userId = getMockUserId();
        StarredNodeResponse response = starredNodeService.toggleStarredStatus(nodeId, isStarred, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/starred")
    public ResponseEntity<List<StarredNodeResponse>> getStarredNodesForCurrentUser() {
        UUID userId = getMockUserId();
        List<StarredNodeResponse> starredNodes = starredNodeService.getStarredNodesForUser(userId);
        return ResponseEntity.ok(starredNodes);
    }
}
