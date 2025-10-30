package com.appShala.userGroupService.Service;

import com.appShala.userGroupService.Enum.GroupSortBy;
import com.appShala.userGroupService.Enum.SortDirection;
import com.appShala.userGroupService.Payload.UserGroupRequest;
import com.appShala.userGroupService.Payload.UserGroupResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface UserGroupService {

    public UserGroupResponse createGroup(UserGroupRequest groupRequest);

    public List<UserGroupResponse> getGroupDetailsByIds(List<UUID> grouoIds);

    public void deleteGroup(UUID groupId);

    public UserGroupResponse updateGroup(UUID groupId, UserGroupRequest request , UUID adminId);

    public Page<UserGroupResponse> getGroups(String userName, GroupSortBy sortBy, SortDirection sortDirection, int page, int size);
}
