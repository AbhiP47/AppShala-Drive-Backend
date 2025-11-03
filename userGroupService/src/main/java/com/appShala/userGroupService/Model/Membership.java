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

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "group_id" , referencedColumnName = "id" , nullable = false)
    private UserGroup group;

    @Column(name = "user_id" , nullable = false)
    private UUID userId;

    @Column(nullable = false)
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
