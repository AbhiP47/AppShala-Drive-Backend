package com.DriveService.DriveService.payloads;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriveNodeUpdateRequest {
    @Size(min=1, max=255, message = "Name should be within 255 letters")
    private String name;
    private String description;
    private Boolean isShared;
    private Boolean isStarred;
    private UUID parentId;
    private UUID nodeType;
}
