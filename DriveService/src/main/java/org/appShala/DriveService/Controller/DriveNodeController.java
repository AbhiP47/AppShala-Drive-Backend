package org.appShala.DriveService.Controller;

import jakarta.validation.Valid;
import org.appShala.DriveService.Payloads.*;
import org.appShala.DriveService.Service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/drive/nodes")
public class DriveNodeController {

    private final DriveNodeService driveNodeService;
    private final StarredNodeService starredNodeService; // Used for star toggle endpoint

    public DriveNodeController(DriveNodeService driveNodeService, StarredNodeService starredNodeService) {
        this.driveNodeService = driveNodeService;
        this.starredNodeService = starredNodeService;
    }

    private UUID getMockUserId() {
        return UUID.fromString("00000000-0000-0000-0000-000000000001");
    }

    @PostMapping
    public ResponseEntity<DriveNodeResponse> createNode(
            @Valid @RequestBody DriveNodeCreationRequest request) {

        UUID userId = getMockUserId();
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
        if (parentId.equals(UUID.fromString("00000000-0000-0000-0000-000000000000"))) {
        }
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
    public ResponseEntity<?> toggleStar(
            @PathVariable UUID nodeId,
            @RequestParam boolean isStarred)
    {
        UUID userId = getMockUserId();
        starredNodeService.toggleStarredStatus(nodeId, isStarred, userId);
        return ResponseEntity.ok().build();
    }
}