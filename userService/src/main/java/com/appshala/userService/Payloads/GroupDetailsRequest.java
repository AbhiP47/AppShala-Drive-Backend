package com.appshala.userService.Payloads;

import lombok.Data;

import java.util.UUID;

@Data
public class GroupDetailsRequest {
    private UUID groupId;
    private String groupName;
}
