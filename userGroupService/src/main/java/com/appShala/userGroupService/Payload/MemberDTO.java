package com.appShala.userGroupService.Payload;

import com.appShala.userGroupService.Enum.MemberRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class MemberDTO {
    private UUID userId;
    private MemberRole role;
    private LocalDateTime joinedAt;
}
