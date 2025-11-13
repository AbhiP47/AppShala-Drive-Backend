package com.DriveService.DriveService.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
 @Data
 @AllArgsConstructor
 @NoArgsConstructor
public class StorageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storageId;
    private String fileName;
    private String fileType;
    private Long size;
    private String uploadedBy;
    private LocalDateTime uploadedAt;
    private String status;
}
