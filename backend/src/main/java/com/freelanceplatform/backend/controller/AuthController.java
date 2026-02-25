package com.freelanceplatform.backend.controller;

import com.freelanceplatform.backend.dto.request.LoginRequest;
import com.freelanceplatform.backend.dto.request.RegisterRequest;
import com.freelanceplatform.backend.dto.response.AuthResponse;
import com.freelanceplatform.backend.entity.User;
import com.freelanceplatform.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.ok("User registered: " + user.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
