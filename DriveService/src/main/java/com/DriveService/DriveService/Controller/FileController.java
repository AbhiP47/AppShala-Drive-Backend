package com.DriveService.DriveService.Controller;

import com.DriveService.DriveService.Client.StorageServiceClient;
import com.DriveService.DriveService.Service.FileService;
import com.DriveService.DriveService.payloads.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("/api/files")
public class FileController {

        @Autowired
        private FileService fileService;
        @Autowired
        private StorageServiceClient storageServiceClient;

        @PostMapping("/upload")
        public ResponseEntity<FileMetadataResponse> uploadFile(
                @RequestParam("file") MultipartFile file,
                @RequestHeader("userId")
                UUID userId) throws IOException {
            FileMetadataResponse response = fileService.uploadFile(file, userId);
            return ResponseEntity.ok(response);
        }

    @GetMapping("/download")
    public ResponseEntity<PresignedUrlResponse> getDownloadUrl(@RequestParam("storageId") String storageId) {
        PresignedUrlResponse urlResp = storageServiceClient.requestDownloadUrl(storageId);
        return ResponseEntity.ok(urlResp);
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<DeleteResponse> deleteFile(
            @RequestParam("storageId") String storageId,
            @RequestHeader("userId") UUID userId) {
        DeleteResponse resp = fileService.deleteFile(storageId,userId);
            return ResponseEntity.ok(resp);
        }
}

