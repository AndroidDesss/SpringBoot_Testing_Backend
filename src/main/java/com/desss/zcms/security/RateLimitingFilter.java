package com.desss.zcms.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// ─────────────────────────────────────────────────────────────────────────────
//  Rate Limiting Filter  (Bucket4j — per-IP, in-memory)
// ─────────────────────────────────────────────────────────────────────────────
@Component
@Slf4j
public class RateLimitingFilter extends OncePerRequestFilter {

    @Value("${app.rate-limit.login-requests-per-minute:5}")
    private int loginRpm;

    @Value("${app.rate-limit.public-requests-per-minute:60}")
    private int publicRpm;

    private final Map<String, Bucket> loginBuckets = new ConcurrentHashMap<>();
    private final Map<String, Bucket> publicBuckets = new ConcurrentHashMap<>();

    // Swagger paths that should never be rate-limited
    private static final List<String> SWAGGER_PATHS = List.of(
            "/swagger-ui", "/v3/api-docs", "/swagger-resources", "/webjars"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {
        String ip = getClientIp(req);
        String path = req.getRequestURI();

        // Skip rate limiting for Swagger UI
        if (SWAGGER_PATHS.stream().anyMatch(path::startsWith)) {
            chain.doFilter(req, res);
            return;
        }

        Bucket bucket = null;

        if (path.startsWith("/api/auth/")) {
            bucket = loginBuckets.computeIfAbsent(ip, k ->
                    Bucket.builder()
                            .addLimit(Bandwidth.builder()
                                    .capacity(loginRpm)
                                    .refillGreedy(loginRpm, Duration.ofMinutes(1))
                                    .build())
                            .build());
        } else if (path.startsWith("/api/public/")) {
            bucket = publicBuckets.computeIfAbsent(ip, k ->
                    Bucket.builder()
                            .addLimit(Bandwidth.builder()
                                    .capacity(publicRpm)
                                    .refillGreedy(publicRpm, Duration.ofMinutes(1))
                                    .build())
                            .build());
        }

        if (bucket != null && !bucket.tryConsume(1)) {
            log.warn("Rate limit exceeded for IP={} path={}", ip, path);
            res.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            res.setContentType("application/json");
            res.getWriter().write("{\"success\":false,\"message\":\"Too many requests. Please try again later.\"}");
            return;
        }

        chain.doFilter(req, res);
    }

    private String getClientIp(HttpServletRequest req) {
        String xff = req.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) return xff.split(",")[0].trim();
        return req.getRemoteAddr();
    }
}
