package org.appShala.DriveService.Payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriveNodeCreationRequest
{
    public String getName;
    @NotBlank(message = "name is required")
    private String name;

    private UUID parentId;
    private Long sizeInBytes;
    private String FileExtension;
    private String NodeType;
    private String OwnerId;
    private UUID parent;

}
