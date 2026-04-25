package com.desss.zcms.service;

import com.desss.zcms.dto.AuthResponse;
import com.desss.zcms.dto.LoginRequest;
import com.desss.zcms.entity.AdminUser;
import com.desss.zcms.repository.AdminUserRepository;
import com.desss.zcms.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
// ─────────────────────────────────────────────────────────────────────────────
//  Auth Service
// ─────────────────────────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authManager;
    private final AdminUserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        AdminUser user = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String access = jwtUtil.generateToken(user);
        String refresh = jwtUtil.generateRefreshToken(user);

        AuthResponse.UserInfo info = new AuthResponse.UserInfo();
        info.setId(user.getId());
        info.setUsername(user.getUsername());
        info.setFullName(user.getFullName());
        info.setEmail(user.getEmail());
        info.setRole(user.getRole());
        info.setWebsiteId(user.getWebsiteId());

        AuthResponse resp = new AuthResponse();
        resp.setAccessToken(access);
        resp.setRefreshToken(refresh);
        resp.setExpiresIn(86400L);
        resp.setUser(info);
        return resp;
    }

    /**
     * Logout: blacklist both tokens so they cannot be reused,
     * even if they haven't expired yet.
     */
    public void logout(String accessToken, String refreshToken) {
        if (accessToken != null && !accessToken.isBlank()) {
            jwtUtil.blacklistToken(accessToken);
        }
        if (refreshToken != null && !refreshToken.isBlank()) {
            jwtUtil.blacklistToken(refreshToken);
        }
        log.info("Tokens blacklisted on logout");
    }

    /**
     * Purge expired tokens from the blacklist every hour to prevent memory growth.
     */
    @Scheduled(fixedRate = 3_600_000)
    public void purgeBlacklist() {
        jwtUtil.purgeExpiredFromBlacklist();
        log.debug("JWT blacklist purge complete");
    }
}
