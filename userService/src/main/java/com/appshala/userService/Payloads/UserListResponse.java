package com.appshala.userService.Payloads;

import com.appshala.userService.Enum.Role;
import com.appshala.userService.Enum.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserListResponse {
    private String name;
    private Role role;
    private String email;
    private LocalDateTime lastActive;
    private Status status;
}
