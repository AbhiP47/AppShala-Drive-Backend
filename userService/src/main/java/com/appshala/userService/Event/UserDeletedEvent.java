package com.appshala.userService.Event;

import java.util.UUID;

public record UserDeletedEvent (
    UUID userId,
    String timestamp
){}

