package com.nexdrive.userService.Payloads;

import com.nexdrive.userService.Enum.Role;
import com.nexdrive.userService.Enum.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCreationRequest {
    @NotBlank(message = "name field cannot be empty")
   private String name;
   @Email(message = "use a correct email format")
   @NotBlank(message = "this field cannot be blank")
    private String email;
    private Role role;
    private Status status;
}
