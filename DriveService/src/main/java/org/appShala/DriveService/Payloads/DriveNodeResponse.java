package org.appShala.DriveService.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriveNodeResponse {
    private UUID id;
    private String Name;
    private String Type;
    private Long sizeInBytes;
    private UUID ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private Boolean isShared;
    private Boolean isStarred;
    private Integer totalNodes;
    private List<DriveNodeResponse> nodes;
    private List<StarredNodeResponse> starredNodes;

}
