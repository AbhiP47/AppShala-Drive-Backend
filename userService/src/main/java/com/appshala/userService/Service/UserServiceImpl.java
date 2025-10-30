package com.appshala.userService.Service;

import com.appshala.userService.Client.GroupServiceClient;
import com.appshala.userService.Enum.Role;
import com.appshala.userService.Enum.SortDirection;
import com.appshala.userService.Enum.Status;
import com.appshala.userService.Enum.UserSortBy;
import com.appshala.userService.Model.User;
import com.appshala.userService.Payloads.GroupDetailsRequest;
import com.appshala.userService.Payloads.UserListResponse;
import com.appshala.userService.Payloads.UserRequest;
import com.appshala.userService.Payloads.UserResponse;
import com.appshala.userService.Repository.UserRepository;
import com.appshala.userService.Security.PasswordGenerator;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    private final GroupServiceClient groupServiceClient;

    public UserServiceImpl(UserRepository userRepository , GroupServiceClient groupServiceClient)
    {
        this.userRepository = userRepository;
        this.groupServiceClient = groupServiceClient;
    }


    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .role(userRequest.getRole())
                .status(userRequest.getStatus())
                .password(PasswordGenerator.generateRandomEncodedPassword())
                .build();
        User savedUser = userRepository.save(user);

        return convertToUserResponse(savedUser);
    }

    private UserResponse convertToUserResponse(User savedUser) {
        return UserResponse.builder()
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .id(savedUser.getId())
                .role(savedUser.getRole())
                .status(savedUser.getStatus())
                .build();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());

    }

    @Override
    public boolean existByEmail(String email) {
        return false;
    }


    public Page<UserListResponse> findUsers(
            Role role,
            Status status,
            String userGroupName, // This parameter is now unused but kept in the signature
            UserSortBy sortBy,
            SortDirection sortDirection,
            int page,
            int size
    ) {
        // Note: The 'userGroupName' parameter is kept in the signature but ignored,

        // Determine Sort Direction
        Sort.Direction direction = sortDirection.getDirection();

        //  Build Sort and Pageable Object
        Sort sort = Sort.by(direction, sortBy.getDbField());
        Pageable pageable = PageRequest.of(page, size, sort);

        //  Execute Query with only Role and Status filters
        return userRepository.findAll(
                buildSpecification(role, status), // Only passing the currently supported filters
                pageable
        ).map(this::convertToUserListResponse);
    }

    @Override
    public UserListResponse convertToUserListResponse(User user) {
        UserListResponse userListResponse = UserListResponse.builder()
                .name(user.getName())
                .role(user.getRole())
                .email(user.getEmail())
                .lastActive(user.getLastActive())
                .status(user.getStatus())
                .build();
        return userListResponse;
    }

    @Override
    public List<UserResponse> createUsers(List<UserRequest> userRequests) {
        List<UserResponse> userResponses = new ArrayList<>();
        for(UserRequest userRequest : userRequests)
        {
            User user =
            User.builder()
                    .name(userRequest.getName())
                    .email(userRequest.getEmail())
                    .role(userRequest.getRole())
                    .password(PasswordGenerator.generateRandomEncodedPassword())
                   .build();
            User savedUser = userRepository.save(user);
            userResponses.add(convertToUserResponse(savedUser));
        }
        return userResponses;
    }

    @Override
    public void deleteUserById(UUID id) {
        User user  = userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with ID :"+id));
        userRepository.delete(user);
    }

    @Override
    public UserResponse updateUserById(UUID id , UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("user not found with ID: "+id));
        if(userRequest.getName() != null)
            user.setName(userRequest.getName());

        if(userRequest.getStatus() != null)
            user.setStatus(userRequest.getStatus());
        User savedUser = userRepository.save(user);
        return convertToUserResponse(savedUser);
    }


    private Specification<User> buildSpecification(
            Role role,
            Status status
    ) {
        return (root, query, criteriaBuilder) -> {
            // List to hold active filter conditions
            List<Predicate> predicates = new ArrayList<>();

            if (role != null) {
                predicates.add(criteriaBuilder.equal(root.get("role"), role));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            // Combine all active predicates with an AND logical operator
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public List<GroupDetailsRequest> getGroupsById(UUID adminId)
    {
        if(adminId == null)
            return Collections.emptyList();
        try {
            List<UUID> groupIds = groupServiceClient.getGroupIdsByUserId(adminId);
            if(groupIds.isEmpty())
                return Collections.emptyList();
            List<GroupDetailsRequest> groupDetails = groupServiceClient.getGroupDetailsByIds(groupIds);
            return groupDetails;
        }
        catch (Exception e)
        {
            System.err.println("Error fetching the group names for admin" + e.getMessage()) ;
            return Collections.emptyList();
        }
    }


}
