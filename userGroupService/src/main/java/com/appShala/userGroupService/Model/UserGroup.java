package com.appShala.userGroupService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false , name = "group_name", unique = true)
    private String name;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at" , columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private LocalDateTime createdAt;

    @Column(name="created_by")
    private UUID createdBy;

    @UpdateTimestamp
    @Column(name = "last_modified_at" , columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private LocalDateTime lastModifiedAt;

    @Column(name = "modified_by")
    private UUID modifiedBy;


}
