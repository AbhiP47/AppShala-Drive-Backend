package com.DriveService.DriveService.Service;

import com.DriveService.DriveService.payloads.DeleteResponse;
import com.DriveService.DriveService.payloads.FileMetadataResponse;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileService {
        FileMetadataResponse uploadFile(MultipartFile file, UUID userId) throws IOException;

        FileMetadataResponse getDownloadUrl(String storageId);

    FileMetadataResponse fetchFileMetadata(String storageId);

    DeleteResponse deleteFile(String storageId, UUID userId);


}
