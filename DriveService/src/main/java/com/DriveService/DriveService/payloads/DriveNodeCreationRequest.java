package com.DriveService.DriveService.payloads;

import com.DriveService.DriveService.Model.DriveNode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriveNodeCreationRequest {

        @NotBlank(message = "name is required")
        private String name;
        private UUID parentId;
        private Long sizeBytes;
        private String fileExtension;
        private UUID nodeType;
        private UUID ownerId;
        private String description;
        private String storageUrl;
}
