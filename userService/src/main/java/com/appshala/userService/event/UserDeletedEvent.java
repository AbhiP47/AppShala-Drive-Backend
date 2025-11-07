package com.appshala.userService.event;

import org.xml.sax.helpers.AttributesImpl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserDeletedEvent(
        UUID userId,
        String timeStamp


) {
}
