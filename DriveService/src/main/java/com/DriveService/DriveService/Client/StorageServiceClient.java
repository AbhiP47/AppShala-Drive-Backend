package com.DriveService.DriveService.Client;

import com.DriveService.DriveService.payloads.DeleteResponse;
import com.DriveService.DriveService.payloads.PresignedUrlResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "STORAGESERVICE" , path = "/api/storage")
public interface StorageServiceClient {

    @GetMapping("/generate-upload-url")
    PresignedUrlResponse requestUploadUrl();

    @GetMapping("/download-url/{storageId}")
    PresignedUrlResponse requestDownloadUrl(@PathVariable("storageId") String storageId);

    @DeleteMapping("/delete/{storageId}")
    DeleteResponse deleteOnStorage(@PathVariable("storageId") String storageId);

}
