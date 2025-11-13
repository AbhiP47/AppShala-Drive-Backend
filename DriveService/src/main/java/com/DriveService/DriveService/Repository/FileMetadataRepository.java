package com.DriveService.DriveService.Repository;

import com.DriveService.DriveService.Model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileMetadataRepository  extends JpaRepository<FileMetadata, UUID> { ;

    FileMetadata findByStorageId(String storageId);
    void deleteByStorageIdAndUploadedBy(String storageId, UUID uploadedBy);

}
