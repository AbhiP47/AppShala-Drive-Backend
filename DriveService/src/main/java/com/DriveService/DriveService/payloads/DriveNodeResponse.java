package com.DriveService.DriveService.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriveNodeResponse {
    private UUID id;
    private String name;
    private Long sizeBytes;
    private UUID ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private Boolean isShared;
    private Boolean isStarred;
    private Integer totalNodes;
    private List<DriveNodeResponse> nodes;
    private List<StarredNodeResponse> starredNodes;
    private UUID nodeType;
    private String description;

}

