package com.gnas.starter.identityservice.infrastructure.out.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
public class PermissionJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String method;

    @Column(name = "path_pattern", nullable = false)
    private String pathPattern;

    @Column(name = "required_role", nullable = false)
    private String requiredRole;

    private String description;
}
