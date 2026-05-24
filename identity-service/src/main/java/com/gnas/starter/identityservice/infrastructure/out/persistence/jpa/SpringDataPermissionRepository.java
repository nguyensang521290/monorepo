package com.gnas.starter.identityservice.infrastructure.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataPermissionRepository extends JpaRepository<PermissionJpaEntity, Long> {
    List<PermissionJpaEntity> findByMethodOrMethod(String method, String any);
}
