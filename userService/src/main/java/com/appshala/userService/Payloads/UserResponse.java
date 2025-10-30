package com.appshala.userService.Payloads;

import com.appshala.userService.Enum.Role;
import com.appshala.userService.Enum.Status;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponse {
    private String name;
    private String email;
    private UUID id;
    private Role role;
    private Status status;
}
