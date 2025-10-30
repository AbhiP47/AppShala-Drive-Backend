package com.appshala.userService.Client;

import com.appshala.userService.Payloads.GroupDetailsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.UUID;

// TO be used in userService for searching users using group filter
@FeignClient(name = "GROUPSERVICE" , path = "/api")
public interface GroupServiceClient {

    // get all the userGroups in which the admin is present
    @GetMapping("/membership/user/{memberrId}")
    List<UUID> getGroupIdsByUserId(@PathVariable("memberId") UUID memberId);

    // get the groups by groupIds
    @GetMapping("/group/names")
    List<GroupDetailsRequest> getGroupDetailsByIds(@RequestParam("groupIds") List<UUID> groupIds);
}
