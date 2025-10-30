package com.appshala.userService.Service;

import com.appshala.userService.Enum.Role;
import com.appshala.userService.Enum.SortDirection;
import com.appshala.userService.Enum.Status;
import com.appshala.userService.Enum.UserSortBy;
import com.appshala.userService.Model.User;
import com.appshala.userService.Payloads.UserListResponse;
import com.appshala.userService.Payloads.UserRequest;
import com.appshala.userService.Payloads.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public UserResponse createUser(UserRequest userRequest);
    public User findByEmail(String email);
    public List<UserResponse> findAll();
    public boolean existByEmail(String email);
    public Page<UserListResponse> findUsers(
            Role role,
            Status status,
            String userGroupName, // Must include this parameter
            UserSortBy sortBy,
            SortDirection sortDirection,
            int page,
            int size
    );
    public UserListResponse convertToUserListResponse(User user);
    public List<UserResponse> createUsers(List<UserRequest> userRequests);
    public void deleteUserById(UUID id);
    public UserResponse updateUserById(UUID id , UserRequest userRequest);
}
