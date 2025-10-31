package org.appShala.DriveService.Payloads;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StarredNodeRequest
{
    @NotNull(message = "isStarred status is required.")
    private Boolean isStarred;

}
