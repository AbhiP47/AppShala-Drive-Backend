package com.appShala.userGroupService.Controller;

import com.appShala.userGroupService.Payload.MembershipResponse;
import com.appShala.userGroupService.Repository.MembershipRepository;
import com.appShala.userGroupService.Service.MembershipService;
import com.appShala.userGroupService.Service.UserGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {

    private final UserGroupService groupService;
    private final MembershipRepository membershipRepository;
    private final MembershipService membershipService;

    public  MembershipController(UserGroupService groupService, MembershipRepository membershipRepository, MembershipService membershipService)
    {
        this.groupService = groupService;
        this.membershipRepository = membershipRepository;
        this.membershipService = membershipService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UUID>> getGroupIdsByUserId(@PathVariable("userId") UUID userId)
    {
        List<UUID> groupIds = membershipRepository.findAllGroupIdsByUserId(userId);
        return ResponseEntity.ok(groupIds);
    }

    // Add membership
    @PostMapping("/addMembers/{groupId}")
    public ResponseEntity<MembershipResponse> addMembership(@RequestBody  List<UUID> userIds , @PathVariable("groupId") UUID groupId , @RequestHeader UUID adminId)
    {
        MembershipResponse response =  membershipService.addMembership(userIds,groupId,adminId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // delete memberships
    @DeleteMapping("/deleteMembers/{groupId}")
    public ResponseEntity<Void> deleteMembership(@RequestBody  List<UUID> userIds , @PathVariable("groupId") UUID groupId , @RequestHeader UUID adminId)
    {
        if(userIds == null || userIds.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        membershipService.deleteMembership(userIds,groupId,adminId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // get user Ids of the members in a group by the group Id for the user service
    @GetMapping("/userId/{groupId}")
    public ResponseEntity<List<UUID>> getMemberUserIdsByGroupId(@PathVariable("groupId") UUID groupId )
    {
        List<UUID> userIds = membershipService.findMemberUserIdsByGroupId(groupId);

        return ResponseEntity.status(HttpStatus.FOUND).body(userIds);
    }

}
