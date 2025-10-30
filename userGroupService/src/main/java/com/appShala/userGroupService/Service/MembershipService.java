package com.appShala.userGroupService.Service;

import com.appShala.userGroupService.Model.Membership;
import com.appShala.userGroupService.Payload.MembershipResponse;

import java.util.List;
import java.util.UUID;

public interface MembershipService {
    public List<Membership> buildAndSaveInitialMemberships(UUID groupId, UUID adminId , List<UUID> initialMembers);
    public List<UUID> getGroupIdsByUserId(UUID userId);
    public MembershipResponse addMembership(List<UUID> userIds , UUID groupId, UUID adminId);

    public void deleteMembership(List<UUID> userIds, UUID groupId, UUID adminId);
}
