package com.DriveService.DriveService.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedNodeResponse {

    private String sharedWithEntity;
    private String permission;

    private String status;
    private UUID nodeId;
    private UUID sharedWithId;
    private String sharedWithName;
    private String sharedWithDescription;
    private UUID sharedBy;
    private LocalDateTime sharedAt;
}
