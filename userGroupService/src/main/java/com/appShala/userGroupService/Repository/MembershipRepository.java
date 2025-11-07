package com.appShala.userGroupService.Repository;

import com.appShala.userGroupService.Enum.MemberRole;
import com.appShala.userGroupService.Model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MembershipRepository extends JpaRepository<Membership , UUID> {

    @Query("SELECT m.groupId FROM Membership m WHERE m.userId = :userId")
    List<UUID> findAllGroupIdsByUserId(UUID userId);

    @Modifying
    @Query("DELETE FROM Membership m WHERE m.groupId = :groupId")
    void deleteByGroupId(UUID groupId);

    @Query("SELECT m.userId FROM Membership m WHERE m.groupId = :groupId")
    List<UUID> findUserIdsByGroupId(UUID groupId);

    @Modifying
    @Query("DELETE FROM Membership m WHERE m.groupId = :groupId AND m.userId IN :userIds")
    void deleteByGroupIdAndUserIdIn(UUID groupId, List<UUID> userIds);

    @Query("SELECT m.userId FROM Membership m WHERE m.groupId = :groupId AND m.role = 'MEMBER'")
    List<UUID> findAllUserIdsByGroupIdAndRoleMember(UUID groupId);



    int deleteByUserId(UUID deletedUserId);
}