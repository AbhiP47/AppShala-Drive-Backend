package com.appShala.userGroupService.ServiceImpl;

import com.appShala.userGroupService.Enum.GroupSortBy;
import com.appShala.userGroupService.Enum.SortDirection;
import com.appShala.userGroupService.Model.Membership;
import com.appShala.userGroupService.Model.UserGroup;
import com.appShala.userGroupService.Payload.UserGroupRequest;
import com.appShala.userGroupService.Payload.UserGroupResponse;
import com.appShala.userGroupService.Repository.MembershipRepository;
import com.appShala.userGroupService.Repository.UserGroupRepository;
import com.appShala.userGroupService.Service.UserGroupService;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final MembershipServiceImpl membershipService;
    private final MembershipRepository membershipRepository;

    public UserGroupServiceImpl(UserGroupRepository userGroupRepository , MembershipServiceImpl membershipService , MembershipRepository membershipRepository) {
        this.userGroupRepository = userGroupRepository;
        this.membershipService = membershipService;
        this.membershipRepository = membershipRepository;
    }

    @Transactional
    @Override
    public UserGroupResponse createGroup(UserGroupRequest groupRequest , UUID adminId) {

        if(userGroupRepository.findByName(groupRequest.getName()).isPresent())
            throw new IllegalArgumentException("Group name already exists");

        UserGroup group = UserGroup.builder()
                .name(groupRequest.getName())
                .createdBy(adminId)
                .build();
        UserGroup savedGroup = userGroupRepository.save(group);

       List<Membership> memberships = membershipService.buildAndSaveInitialMemberships(
               savedGroup,
               savedGroup.getCreatedBy(),
               groupRequest.getInitialMembers()

       );
        UserGroupResponse groupResponse = UserGroupResponse.builder()
                .groupId(savedGroup.getId())
                .name(savedGroup.getName())
                .memberCount(memberships.size())
                .build();
        return groupResponse;
    }

    @Override
    public List<UserGroupResponse> getGroupDetailsByIds(List<UUID> groupIds) {
        if(groupIds == null || groupIds.isEmpty())
        {
            return Collections.emptyList();
        }
        List<UserGroup> groups = userGroupRepository.findAllById(groupIds);
        return groups.stream()
                .map(group -> new UserGroupResponse(group.getId(), group.getName()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteGroup(UUID groupId) {
        if(userGroupRepository.existsById(groupId))
            throw new RuntimeException("Group not found");

        userGroupRepository.deleteById(groupId);
        membershipRepository.deleteByGroupId(groupId);
    }

    @Override
    @Transactional
    public UserGroupResponse updateGroup(UUID groupId, UserGroupRequest request, UUID adminId) {
        UserGroup group = userGroupRepository.findById(groupId).orElseThrow(()-> new RuntimeException(("Group not found")));
        if(request.getName() == null || request.getName().isEmpty())
        {
            throw new RuntimeException("Group name not provided");
        }
        if(userGroupRepository.findByName(request.getName()).isPresent())
            throw new RuntimeException("Group name already exists");
        group.setName(request.getName());
        UserGroup savedGroup = userGroupRepository.save(group);
        UserGroupResponse response = UserGroupResponse.builder()
                .groupId(groupId)
                .name(group.getName())
                .build();
        return response;
    }

    @Override
    public Page<UserGroupResponse> getGroups(String userName, GroupSortBy sortBy, SortDirection sortDirection, int page, int size) {
        Specification<UserGroup> spec = Specification.where(null);

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public UUID findGroupIdByName(String groupName, UUID adminId) {
        Optional<UUID> groupId = userGroupRepository.findByNameAndCreatedBy(groupName,adminId);
        if(groupId.isEmpty())
            throw new RuntimeException("Group not found for the Group Name : "+groupName);
        else
        return groupId.get();
    }

    private Specification<UserGroup> groupsByMembership(List<UUID> userIds) {
        return (root, query, cb) -> {
            Subquery<UUID> subquery = query.subquery(UUID.class);
            Root<Membership> membershipRoot = subquery.from(Membership.class);

            subquery.select(membershipRoot.get("groupId")) // Select the Group ID from Membership table
                    .where(membershipRoot.get("userId").in(userIds)); // Where User ID is in the list

            // The main query includes the UserGroup only if its ID exists in the subquery result
            return cb.in(root.get("id")).value(subquery);
        };
    }

}
