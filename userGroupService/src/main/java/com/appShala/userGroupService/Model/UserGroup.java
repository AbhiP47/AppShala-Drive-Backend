package com.appShala.userGroupService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_group")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Column(nullable = false , name="name" ,  unique = true)
    private String name;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at" , columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private ZonedDateTime createdAt;

    @Column(name="created_by")
    private UUID createdBy;

    @UpdateTimestamp
    @Column(name = "last_modified_at" , columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private ZonedDateTime lastModifiedAt;

    @Column(name = "modified_by")
    private UUID modifiedBy;


}
