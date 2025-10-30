package com.appShala.userGroupService.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UserGroupResponse {
    private UUID groupId;

    private String name;

    private  Integer memberCount;

    public UserGroupResponse(UUID id, String name) {
    }
}
