package com.DriveService.DriveService.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileMetadataResponse {
    private UUID Id;
    private String fileName;
    private String storageId;
    private String size;
    private String createdAt;
    private UUID createdBy;

    public FileMetadataResponse(UUID id, String storageId, String fileName, String fileType, long size, LocalDateTime uploadedAt, UUID uploadedBy) {
    }
}
