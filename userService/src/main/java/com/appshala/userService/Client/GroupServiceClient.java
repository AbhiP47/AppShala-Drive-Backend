package com.appshala.userService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

// TO be used in userService for searching users using group filter
@FeignClient(name = "USERGROUPSERVICE" , path = "/api")
public interface GroupServiceClient {

    // get the groupId by groupName
    @GetMapping("group/groupId/{groupName}")
    UUID getGroupIdByName(@PathVariable("groupName") String groupName , @RequestHeader("adminId") UUID callingAdminId);

    @GetMapping("membership/userId/{groupId}")
    List<UUID> getMemberUserIdsByGroupId(@PathVariable("groupId") UUID groupId );
}
