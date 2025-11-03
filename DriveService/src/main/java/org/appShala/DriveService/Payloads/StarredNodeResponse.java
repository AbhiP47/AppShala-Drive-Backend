package org.appShala.DriveService.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarredNodeResponse
{
    private UUID nodeId;
    private UUID starredBy;
    private Boolean IsStarred;
    private LocalDateTime StarredAt;
    private LocalDateTime StarredWith;
}
