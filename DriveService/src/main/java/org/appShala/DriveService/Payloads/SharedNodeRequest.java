package org.appShala.DriveService.Payloads;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SharedNodeRequest
{
    private String nodeId;
    @NotNull(message = "The ID of the recipient user or group is required.")
    private UUID sharedWithId;
    private String sharedNodeName;
    private String sharedNodeDescription;
    private ZonedDateTime sharedNodeLastModifiedAt;
    @NotNull(message = "Recipient entity type is required.")
    private String sharedWith;
    @NotNull(message="Permission level is required ")
    private String Permission;
}
