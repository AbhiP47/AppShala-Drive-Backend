package org.appShala.DriveService.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedNodeResponse
{
    private String Permission;
    private String status;
    private UUID nodeId;
    private UUID SharedWithId;
    private String SharedWithName;
    private String SharedWithDescription;
    private UUID SharedBy;
    private LocalDateTime SharedAt;
    private String SharedWith;
}
