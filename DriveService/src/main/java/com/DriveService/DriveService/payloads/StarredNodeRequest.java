package com.DriveService.DriveService.payloads;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StarredNodeRequest {
    @NotNull(message = "isStarred status is required.")
    private Boolean isStarred;
    private UUID nodeId;
}
