package com.appShala.userGroupService.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class MembershipResponse {
    private List<MemberDTO> members;
    private UUID groupId;
    private UUID adminId;
}
