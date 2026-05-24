package com.gnas.starter.identityservice.application.service;

import com.gnas.starter.identityservice.infrastructure.out.persistence.jpa.PermissionJpaEntity;
import com.gnas.starter.identityservice.infrastructure.out.persistence.jpa.SpringDataPermissionRepository;
import com.gnas.starter.identityservice.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionService {

    private final JwtService jwtService;
    private final SpringDataPermissionRepository permissionRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public boolean isAuthorized(String method, String path, String token) {
        log.info("isAuthorized call: method={}, path={}, tokenPresent={}", method, path, token != null);
        String role = null;
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            try {
                if (jwtService.isTokenValid(jwt, null)) {
                    role = jwtService.extractClaim(jwt, claims -> claims.get("role", String.class));
                }
            } catch (Exception e) {
                log.error("Error validating token for authorization: {}", e.getMessage());
            }
        }

        log.info("Checking database permission for role: {}, method: {}, path: {}", role, method, path);

        List<PermissionJpaEntity> relevantPermissions = permissionRepository.findByMethodOrMethod(method.toUpperCase(), "ANY");
        log.info("Found {} relevant permissions in DB", relevantPermissions.size());

        for (PermissionJpaEntity permission : relevantPermissions) {
            boolean pathMatch = pathMatcher.match(permission.getPathPattern(), path);
            log.info("Matching path '{}' with pattern '{}': {}", path, permission.getPathPattern(), pathMatch);
            if (pathMatch) {
                if ("ANY".equals(permission.getRequiredRole()) || (role != null && permission.getRequiredRole().equals(role))) {
                    log.info("Authorization GRANTED by rule: {}", permission.getDescription());
                    return true;
                }
            }
        }

        log.warn("Authorization DENIED for role: {}, method: {}, path: {}", role, method, path);
        return false;
    }
}
