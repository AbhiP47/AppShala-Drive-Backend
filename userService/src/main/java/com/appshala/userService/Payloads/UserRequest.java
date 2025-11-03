package com.appshala.userService.Payloads;

import com.appshala.userService.Enum.Role;
import com.appshala.userService.Enum.Status;
import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private Role role;
    private Status status;
}
