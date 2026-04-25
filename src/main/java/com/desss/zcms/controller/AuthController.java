package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.AuthResponse;
import com.desss.zcms.dto.LoginRequest;
import com.desss.zcms.dto.LogoutRequest;
import com.desss.zcms.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// ─────────────────────────────────────────────────────────────────────────────
//  Auth Controller   /api/auth
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Login and logout endpoints")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login", description = "Authenticate with username/password. Returns JWT access + refresh tokens.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Login successful", authService.login(req)));
    }

    @Operation(summary = "Logout", description = "Blacklists both access and refresh tokens immediately. Client should also clear local storage.")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody LogoutRequest req) {
        authService.logout(req.getAccessToken(), req.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.ok("Logged out successfully", null));
    }
}
