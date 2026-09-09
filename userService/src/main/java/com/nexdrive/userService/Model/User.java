package com.nexdrive.userService.Model;

import com.nexdrive.userService.Enum.Role;
import com.nexdrive.userService.Enum.Status;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id ;

    @Column(nullable = false )
    private String name;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(nullable = false )
    private String password;

    @Column(name ="email_verified")
    private boolean emailVerified = false;

    @Column(name = "phone_number" , length = 50)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(
            write = "?::user_status",
            read = "status::text"
    )
    private Status status;

    @Column(nullable = false )
    private boolean isEnabled;

    @Column(nullable = false)
    private boolean isBlocked;

    @UpdateTimestamp
    @Column(name="last_active" , nullable = false)
    private LocalDateTime lastActive;

    @CreationTimestamp
    @Column(name="created_at" , updatable = false , nullable = false , columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private ZonedDateTime createdAt;

    @Column(name = "created_by" , nullable = false)
    private UUID createdBy;

    @UpdateTimestamp
    @Column(name = "updated_at" , nullable = false  ,columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private ZonedDateTime updatedAt;

    @Column(name = "updated_by" , nullable = false)
    private UUID updatedBy;

    @Column(name = "profile_picture")
    private String profilePicture;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    @Column(name= "invitation_token" , unique = true)
    String invitationToken;

    @Column(name ="token_expires_at", nullable = true)
    LocalDateTime tokenExpiresAt;
}
