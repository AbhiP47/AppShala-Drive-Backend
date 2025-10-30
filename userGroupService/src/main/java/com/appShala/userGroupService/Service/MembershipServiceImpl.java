package com.appShala.userGroupService.Service;

import com.appShala.userGroupService.Enum.MemberRole;
import com.appShala.userGroupService.Model.Membership;
import com.appShala.userGroupService.Payload.MemberDTO;
import com.appShala.userGroupService.Payload.MembershipResponse;
import com.appShala.userGroupService.Repository.MembershipRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MembershipServiceImpl implements MembershipService{

    private final  MembershipRepository membershipRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository)
    {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public List<Membership> buildAndSaveInitialMemberships(UUID groupId, UUID adminId, List<UUID> initialMembers) {
        Set<UUID> allMemberIds = new HashSet<>(initialMembers);
        allMemberIds.add(adminId);

        List<Membership> memberships = allMemberIds.stream()
                .map(userId -> Membership.builder()
                        .groupId(groupId)
                        .userId(userId)
                        .role(userId.equals(adminId) ? MemberRole.MANAGER : MemberRole.MEMBER)
                        .joinedAt(LocalDateTime.now())
                        .build()
                ).collect(Collectors.toList());
        return membershipRepository.saveAll(memberships);
    }

    @Transactional(readOnly = true)
    public List<UUID> getGroupIdsByUserId(UUID userId)
    {
        if(userId == null)
            return Collections.emptyList();

        return membershipRepository.findAllGroupIdsByUserId(userId);
    }

    @Override
    @Transactional
    public MembershipResponse addMembership(List<UUID> userIds, UUID groupId, UUID adminId) {
        if(userIds == null || userIds.isEmpty())
             throw new RuntimeException("no user to add");

        List<UUID> existingMemberIds = membershipRepository.findUserIdsByGroupId(groupId);
        List<UUID> newMemberIds = userIds.stream()
                .filter(id -> !existingMemberIds.contains(id))
                .collect(Collectors.toList());
        if(newMemberIds.isEmpty())
            throw new IllegalArgumentException("All provided users are already present");
        List<Membership> newMemberships = newMemberIds.stream()
                .map(userId -> Membership.builder()
                        .groupId(groupId)
                        .userId(userId)
                        .role(MemberRole.MEMBER)
                        .joinedAt(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        List<Membership> savedMemberships = membershipRepository.saveAll(newMemberships);

        return  convertToMembershipResponse(savedMemberships , adminId , groupId);
    }

    @Override
    @Transactional
    public void deleteMembership(List<UUID> userIds, UUID groupId, UUID adminId) {
        membershipRepository.deleteByGroupIdAndUserIdIn(groupId,userIds);
    }

    private MembershipResponse convertToMembershipResponse(List<Membership> savedMemberships , UUID adminId , UUID groupId) {

List<MemberDTO> memberDetails = savedMemberships.stream().map(membership ->
        MemberDTO.builder()
                .userId(membership.getUserId())
                .role(membership.getRole())
                .joinedAt(membership.getJoinedAt())
                .build())
        .collect(Collectors.toList());
MembershipResponse response = MembershipResponse.builder()
        .adminId(adminId)
        .groupId(groupId)
        .members(memberDetails)
        .build();
return response;
    }


}
