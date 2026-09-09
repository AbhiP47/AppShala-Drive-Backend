package com.nexdrive.userService.Payloads;

import com.nexdrive.userService.Enum.Role;
import com.nexdrive.userService.Enum.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String name;
    private String email;
    private Role role;
    private Status status;
}
