package com.appShala.userGroupService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="USERSERVICE" , path="/api/user")
public interface UserServiceClient {

    @GetMapping("/userExistsById/{userId}")
    public boolean userExistById(@PathVariable("userId") UUID userId);
}
