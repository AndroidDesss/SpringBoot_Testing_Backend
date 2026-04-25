package com.desss.zcms.security;

import com.desss.zcms.entity.AdminUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    @Value("${app.jwt.refresh-expiration}")
    private long refreshExpiration;

    /**
     * In-memory blacklist for invalidated tokens (logout / password change).
     * In production, replace with Redis for multi-instance support.
     */
    private final Set<String> blacklistedTokens = Collections.newSetFromMap(new ConcurrentHashMap<>());

    // ── Key ───────────────────────────────────────────────────────────────────

    /**
     * Expects JWT_SECRET to be a Base64-encoded 256-bit key.
     * Generate one with: openssl rand -base64 32
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // ── Token generation ──────────────────────────────────────────────────────

    public String generateToken(AdminUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("websiteId", user.getWebsiteId());
        claims.put("fullName", user.getFullName());
        return buildToken(claims, user.getUsername(), jwtExpiration);
    }

    public String generateRefreshToken(AdminUser user) {
        return buildToken(new HashMap<>(), user.getUsername(), refreshExpiration);
    }

    private String buildToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts.builder()
            .claims(claims)
            .subject(subject)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSigningKey())
            .compact();
    }

    // ── Token validation ──────────────────────────────────────────────────────

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            if (blacklistedTokens.contains(token)) return false;
            String tokenUsername = extractUsername(token);
            return tokenUsername.equals(username) && !isTokenExpired(token);
        } catch (JwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    /** Call on logout or password change to invalidate a token immediately. */
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
        // Optionally: schedule cleanup of expired tokens to avoid memory growth
    }

    /** Remove expired tokens from the blacklist periodically (called by scheduler). */
    public void purgeExpiredFromBlacklist() {
        blacklistedTokens.removeIf(token -> {
            try {
                return isTokenExpired(token);
            } catch (Exception e) {
                return true; // remove unparseable tokens too
            }
        });
    }

    private boolean isTokenExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
