package com.DriveService.DriveService.storageClient;

import com.DriveService.DriveService.payloads.PresignedUrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class StorageServiceClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${storageService.url}")
    private String storageUrl;

    public PresignedUrlResponse requestUploadUrl(String storageId){
        String url = storageUrl + "api/storage/generate-upload-url";
        return restTemplate.postForObject(url, null, PresignedUrlResponse.class);
    }
    public PresignedUrlResponse requestDownloadUrl(String storageId) {
        String url = storageUrl + "api/storage/download-url" + storageId;
        return restTemplate.getForObject(url, PresignedUrlResponse.class);
    }
    public void deleteOnStorage(String storageId) {
        String url = storageUrl + "/api/storage/delete/" + storageId;
        restTemplate.delete(url);
    }
    public void uploadToPresignedUrl(String presignedUrl, HttpEntity<byte[]> requestEntity) {
        restTemplate.exchange(presignedUrl, HttpMethod.PUT, requestEntity, String.class);
    }

}
