package com.appshala.userService.Payloads;

import com.appshala.userService.Enum.Role;
import com.appshala.userService.Enum.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
   private String name;
   @Email(message = "use a correct email format")
   @NotBlank(message = "this field cannot be blank")
    private String email;
    private Role role;
    private Status status;
}
