package com.gnas.starter.identityservice.infrastructure.in.rest;

import com.gnas.starter.identityservice.application.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final PermissionService permissionService;

    @PostMapping("/authorize")
    public ResponseEntity<Void> authorize(@RequestBody AuthorizationRequest request) {
        if (permissionService.isAuthorized(request.method(), request.path(), request.token())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
