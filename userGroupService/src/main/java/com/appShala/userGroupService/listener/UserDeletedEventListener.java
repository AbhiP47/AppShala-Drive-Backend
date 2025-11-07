
package com.appShala.userGroupService.listener;


import com.appShala.userGroupService.Repository.MembershipRepository;

import com.appShala.userGroupService.event.UserDeletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Slf4j
public class UserDeletedEventListener {

    private final MembershipRepository membershipRepository;

    public UserDeletedEventListener(MembershipRepository membershipRepository)
    {
        this.membershipRepository = membershipRepository;
    }

    @KafkaListener(topics = "${kafka-topic.user-events}", groupId ="${spring.kafka.consumer.group-id}" )
    @Transactional
    public void handleUserDeletedEvent(UserDeletedEvent event)
    {
        UUID deletedUserId = event.userId();
        log.info("CONSUMER : Received UserDeletedEvent for the User ID: {}  ",deletedUserId);
        int count = membershipRepository.deleteByUserId(deletedUserId);
        log.info("CONSUMER: Cleaned up {} memberships associated with user ID : {} and Deletion timestamp : {}", count, deletedUserId , event.timeStamp());
    }
}