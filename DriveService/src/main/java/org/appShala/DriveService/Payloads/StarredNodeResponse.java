package org.appShala.DriveService.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarredNodeResponse
{
    private UUID nodeId;
    private UUID sharedBy;
    private Boolean IsShared;
    private ZonedDateTime SharedAt;
    private ZonedDateTime SharedWith;
}
