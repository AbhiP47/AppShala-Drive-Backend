package com.appshala.userService.event;

import org.xml.sax.helpers.AttributesImpl;

import java.util.UUID;

public record UserDeletedEvent(
        UUID userId,


) {
}
