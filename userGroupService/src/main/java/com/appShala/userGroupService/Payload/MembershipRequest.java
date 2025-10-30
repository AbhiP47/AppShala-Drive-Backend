package com.appShala.userGroupService.Payload;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MembershipRequest {

    private List<UUID> members;
    private UUID groupId;
    private UUID adminId;

}
