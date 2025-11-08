package com.appShala.userGroupService.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserGroupRequest {
    private String name;
    private List<UUID> initialMembers;
}
