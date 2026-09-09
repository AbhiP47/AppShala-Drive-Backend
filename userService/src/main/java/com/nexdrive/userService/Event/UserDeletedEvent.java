package com.nexdrive.userService.Event;

import java.util.UUID;

public record UserDeletedEvent (
    UUID userId,
    String timestamp
){}

