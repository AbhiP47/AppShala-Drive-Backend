package com.DriveService.DriveService.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class DeleteResponse {
    private String storageId;
    private boolean success;
    private String message;

    public DeleteResponse(String storageId, boolean b, String fileDeleted) {
    }
}
