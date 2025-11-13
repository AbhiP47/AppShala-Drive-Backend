package com.DriveService.DriveService.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadRequest {
    private String fileName;
    private String fileType;
    private MultipartFile file;
    private String storageId;
}
