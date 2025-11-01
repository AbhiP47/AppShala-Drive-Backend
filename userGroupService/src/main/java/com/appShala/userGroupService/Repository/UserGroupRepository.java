package com.appShala.userGroupService.Repository;

import com.appShala.userGroupService.Model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup , UUID> {
    Optional<UserGroup> findByName(String name);

    Optional<UUID> findByNameAndCreatedBy(String groupName, java.util.UUID adminId);
}
