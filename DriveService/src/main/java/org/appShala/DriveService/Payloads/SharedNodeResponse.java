package org.appShala.DriveService.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedNodeResponse
{
    private String Permission;
    private String status;
    private UUID nodeId;
    private String SharedWithId;
    private String SharedWithName;
    private String SharedWithDescription;
    private String SharedWith;
    private UUID SharedBy;
    private ZonedDateTime SharedAt;
}
