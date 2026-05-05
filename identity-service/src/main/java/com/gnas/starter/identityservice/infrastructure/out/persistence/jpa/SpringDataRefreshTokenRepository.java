package com.gnas.starter.identityservice.infrastructure.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataRefreshTokenRepository extends JpaRepository<RefreshTokenJpaEntity, Long> {
    Optional<RefreshTokenJpaEntity> findByToken(String token);
    void deleteByUsername(String username);
}
