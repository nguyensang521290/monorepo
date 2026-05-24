package com.gnas.starter.identityservice.application.service;

import com.gnas.starter.identityservice.domain.Role;
import com.gnas.starter.identityservice.infrastructure.out.persistence.jpa.RefreshTokenJpaEntity;
import com.gnas.starter.identityservice.infrastructure.out.persistence.jpa.SpringDataRefreshTokenRepository;
import com.gnas.starter.identityservice.infrastructure.out.persistence.jpa.SpringDataUserRepository;
import com.gnas.starter.identityservice.infrastructure.out.persistence.jpa.UserJpaEntity;
import com.gnas.starter.identityservice.infrastructure.security.JwtService;
import com.gnas.starter.identityservice.infrastructure.in.rest.AuthRequest;
import com.gnas.starter.identityservice.infrastructure.in.rest.AuthResponse;
import com.gnas.starter.identityservice.infrastructure.in.rest.RegisterRequest;
import com.gnas.starter.identityservice.infrastructure.in.rest.RefreshTokenRequest;
import com.gnas.starter.identityservice.infrastructure.out.event.outbox.UserRegisteredOutboxWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final SpringDataUserRepository userRepository;
    private final SpringDataRefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRegisteredOutboxWriter outboxWriter;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        var user = new UserJpaEntity();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        if (request.role() != null) {
            user.setRole(request.role());
        }
        
        var savedUser = userRepository.save(user);

        outboxWriter.write(savedUser);
        
        return generateAuthResponse(savedUser);
    }

    @Transactional
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        var user = userRepository.findByUsername(request.username())
                .orElseThrow();
        
        return generateAuthResponse(user);
    }

    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        var refreshToken = refreshTokenRepository.findByToken(request.refreshToken())
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (refreshToken.isRevoked() || refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token is expired or revoked");
        }

        var user = userRepository.findByUsername(refreshToken.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Rotate refresh token
        refreshTokenRepository.delete(refreshToken);
        return generateAuthResponse(user);
    }

    private AuthResponse generateAuthResponse(UserJpaEntity user) {
        UserDetails userDetails = userDetailsService().loadUserByUsername(user.getUsername());
        var accessToken = jwtService.generateToken(userDetails);
        var refreshToken = createRefreshToken(user.getUsername());
        return new AuthResponse(user.getId(), accessToken, refreshToken.getToken());
    }

    private UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        java.util.Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                ))
                .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException("User not found"));
    }

    private RefreshTokenJpaEntity createRefreshToken(String username) {
        // Optional: clean up old tokens for this user
        refreshTokenRepository.deleteByUsername(username);

        var refreshToken = new RefreshTokenJpaEntity();
        refreshToken.setUsername(username);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtService.getRefreshTokenExpiration()));
        
        return refreshTokenRepository.save(refreshToken);
    }
}
