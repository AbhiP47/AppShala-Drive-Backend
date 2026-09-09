package com.nexdrive.userService.Payloads;

import com.nexdrive.userService.Enum.Role;
import com.nexdrive.userService.Enum.Status;
import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private Role role;
    private Status status;
}
