package com.DriveService.DriveService.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresignedUrlResponse {
    private String storageId;
    private String presignedUrl;
}
