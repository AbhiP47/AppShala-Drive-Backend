package com.appShala.userGroupService.event;

import java.util.UUID;

public record UserDeletedEvent(
        UUID userId,
        String timeStamp
) {}
