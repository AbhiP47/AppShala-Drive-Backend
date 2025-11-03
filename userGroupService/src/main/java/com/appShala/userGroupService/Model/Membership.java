package com.appShala.userGroupService.Model;

import com.appShala.userGroupService.Enum.MemberRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(name = "group_id" , nullable = false)
    private UUID groupId;

    @Column(name = "user_id" , nullable = false)
    private UUID userId;

    @Column(name = "member_role" , nullable = false)
    @ColumnTransformer(
            write = "?::member_role",
            read = "role::text"
    )
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @CreationTimestamp
    @Column(name = "joined_at", updatable = false , nullable = false ,columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private ZonedDateTime joinedAt;
}
