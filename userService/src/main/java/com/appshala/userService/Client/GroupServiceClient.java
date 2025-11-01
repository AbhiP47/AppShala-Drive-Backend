package com.appshala.userService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

// TO be used in userService for searching users using group filter
@FeignClient(name = "GROUPSERVICE" , path = "/api/group")
public interface GroupServiceClient {

    // get all the members by the groupId
    @GetMapping("/membership/user/{groupId}")
    List<UUID> getMemberUserIdsByGroupId(@PathVariable("groupId") UUID groupId);

    // get the groupId by groupName
    @GetMapping("/group/{groupName}")
      UUID  getGroupIdByName(@PathVariable("groupName") String groupName , @RequestHeader("adminId") UUID callingAdminId);
}
