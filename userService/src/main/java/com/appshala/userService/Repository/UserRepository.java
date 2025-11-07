package com.appshala.userService.Repository;

import com.appshala.userService.Enum.Role;
import com.appshala.userService.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> , JpaSpecificationExecutor<User> {

    User findByEmail(String email);

    Set<String> findExistingEmails(ArrayList<String> strings);

    Optional<Role> findRoleById(UUID userId);

    List<User> findAllByCreatedBy(UUID adminId);
}
