package com.DriveService.DriveService.ServiceImpl;

import com.DriveService.DriveService.Model.FileMetadata;
import com.DriveService.DriveService.Repository.FileMetadataRepository;
import com.DriveService.DriveService.Service.FileService;
import com.DriveService.DriveService.payloads.DeleteResponse;
import com.DriveService.DriveService.payloads.FileMetadataResponse;
import com.DriveService.DriveService.payloads.PresignedUrlResponse;
import com.DriveService.DriveService.Client.StorageServiceClient;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService
{
    @Autowired
    private StorageServiceClient storageServiceClient;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    @Override
    @Transactional
    public FileMetadataResponse uploadFile(MultipartFile file, UUID userId) throws IOException
    {
        PresignedUrlResponse urlResp = storageServiceClient.requestUploadUrl();
        uploadToPresignedUrl(urlResp.getPresignedUrl(), file);

        FileMetadata meta =new FileMetadata();
        meta.setStorageId(urlResp.getStorageId());
        meta.setFileName(file.getOriginalFilename());
        meta.setFileType(file.getContentType());
        meta.setSize(file.getSize());
        meta.setUploadedBy(userId);
        meta.setUploadedAt(LocalDateTime.now());

        fileMetadataRepository.save(meta);
        log.info("UPLOAD : User uploading file: {}", userId);
        log.error("UPLOAD : Error Uploading File: {]" , userId);
        return new FileMetadataResponse(meta.getId(), meta.getStorageId(),
                meta.getFileName(), meta.getFileType(), meta.getSize(),
                meta.getUploadedAt(), meta.getUploadedBy());
    }

    @Override
    @Transactional
    public FileMetadataResponse getDownloadUrl(String storageId) {
        return null;
    }

    @Override
    @Transactional
    public FileMetadataResponse fetchFileMetadata(String storageId) {
        FileMetadata meta = fileMetadataRepository.findByStorageId(storageId);
        PresignedUrlResponse UrlResp = storageServiceClient.requestDownloadUrl(storageId) ;

        log.info("FETCH : Fetching file metadata: {}", storageId);
        return new FileMetadataResponse(meta.getId(), meta.getStorageId(), meta.getFileName(), meta.getFileType(),meta.getSize(),meta.getUploadedAt(),meta.getUploadedBy());
    }

    @Override
    @Transactional
    public DeleteResponse deleteFile(String storageId, UUID userId) {
        storageServiceClient.deleteOnStorage(storageId);
        fileMetadataRepository.deleteByStorageIdAndUploadedBy(storageId,userId);
        log.info("DELETE : Deleting file: {}", storageId);
        log.error("DELETE : Error deleting file: {}", storageId);
        return new DeleteResponse();
    }

    public void uploadToPresignedUrl(String presignedUrl, MultipartFile file) throws IOException {
        WebClient webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, file.getContentType())
                .build();

        webClient.put()
                .uri(presignedUrl)
                .bodyValue(file.getBytes())
                .retrieve()
                .toBodilessEntity()
                .block();
    }


}
