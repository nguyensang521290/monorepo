package com.gnas.starter.identityservice.application.service;

import com.gnas.starter.identityservice.infrastructure.out.persistence.jpa.RefreshTokenJpaEntity;
import com.gnas.starter.identityservice.infrastructure.out.persistence.jpa.SpringDataRefreshTokenRepository;
import com.gnas.starter.identityservice.infrastructure.out.persistence.jpa.SpringDataUserRepository;
import com.gnas.starter.identityservice.infrastructure.out.persistence.jpa.UserJpaEntity;
import com.gnas.starter.identityservice.infrastructure.security.JwtService;
import com.gnas.starter.identityservice.infrastructure.in.rest.AuthRequest;
import com.gnas.starter.identityservice.infrastructure.in.rest.AuthResponse;
import com.gnas.starter.identityservice.infrastructure.in.rest.RegisterRequest;
import com.gnas.starter.identityservice.infrastructure.in.rest.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
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
        
        userRepository.save(user);
        
        return generateAuthResponse(user.getUsername(), user.getPassword());
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
        
        return generateAuthResponse(user.getUsername(), user.getPassword());
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
        return generateAuthResponse(user.getUsername(), user.getPassword());
    }

    private AuthResponse generateAuthResponse(String username, String password) {
        var userDetails = new User(username, password, new ArrayList<>());
        var accessToken = jwtService.generateToken(userDetails);
        var refreshToken = createRefreshToken(username);
        return new AuthResponse(accessToken, refreshToken.getToken());
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
