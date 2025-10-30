package com.appshala.userService.Controller;

import com.appshala.userService.Enum.Role;
import com.appshala.userService.Enum.SortDirection;
import com.appshala.userService.Enum.Status;
import com.appshala.userService.Enum.UserSortBy;
import com.appshala.userService.Payloads.UserListResponse;
import com.appshala.userService.Payloads.UserRequest;
import com.appshala.userService.Payloads.UserResponse;
import com.appshala.userService.Service.UserService;
import com.appshala.userService.Service.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponse>  createUser(@RequestBody UserRequest userRequest)
    {
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse) ;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponse>>  getUsersList()
    {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/getUsers")
    public Page<UserListResponse> listUsers(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String userGroupName,
            @RequestParam(defaultValue = "byName") UserSortBy sortBy,
            @RequestParam(defaultValue = "A_TO_Z") SortDirection sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
            )
    {
        return userService.findUsers(role,status,userGroupName,sortBy,sortDirection,page,size);
    }

    @PostMapping("/createUsers")
    public ResponseEntity<List<UserResponse>> createUsers(@RequestBody List<UserRequest> userRequests)
    {
        List<UserResponse> userResponses = userService.createUsers(userRequests);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponses);
    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable UUID id)
    {
        try{
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        }
        catch (UsernameNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable UUID id , @RequestBody UserRequest userRequest)
    {
        UserResponse userResponse =  userService.updateUserById(id,userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }


//    @PostMapping("/bulk-import")
//    public ResponseEntity<String> bulkImportUsers(@RequestPart("file") MultipartFile file)
//    {
//        if(file.isEmpty()){
//            return ResponseEntity.badRequest();
//        }
//    }
}
