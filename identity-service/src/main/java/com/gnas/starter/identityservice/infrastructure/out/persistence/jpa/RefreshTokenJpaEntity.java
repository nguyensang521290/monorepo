package com.gnas.starter.identityservice.infrastructure.out.persistence.jpa;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Data
public class RefreshTokenJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Instant expiryDate;

    private boolean revoked;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();
}
