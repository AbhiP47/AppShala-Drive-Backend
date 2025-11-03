package com.appshala.userService.Model;

import com.appshala.userService.Enum.Role;
import com.appshala.userService.Enum.Status;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(
            write = "?::user_role",
            read = "role::text"
    )
    private Role role;
}
