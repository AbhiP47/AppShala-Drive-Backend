package org.appShala.DriveService.Payloads;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriveNodeUpdateRequest
{
    @Size(min=1, max=255 , message = "Name should be within 255 letters")
    private String name;
    private String description;
    private ZonedDateTime createdAt;
    private ZonedDateTime lastModifiedAt;
    private Boolean isShared;
    private Boolean isStarred;
    private Integer totalNodes;
    private List<StarredNodeResponse> starredNodes;
    private UUID newParentId;

}
