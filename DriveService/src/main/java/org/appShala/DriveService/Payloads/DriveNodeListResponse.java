package org.appShala.DriveService.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriveNodeListResponse
{
    private Integer totalNodes;
    private List<DriveNodeResponse> nodes;
    private List<StarredNodeResponse> starredNodes;

}
