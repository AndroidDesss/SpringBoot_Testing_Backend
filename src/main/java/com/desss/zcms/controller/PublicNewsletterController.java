package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.NewsletterSubscribeRequest;
import com.desss.zcms.entity.Newsletter;
import com.desss.zcms.repository.NewsletterRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ─────────────────────────────────────────────────────────────────────────────
//  Public Newsletter
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/public/newsletter")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Public - Newsletter", description = "Newsletter subscription and admin listing")
public class PublicNewsletterController {

    private final NewsletterRepository repo;

    @Operation(summary = "Subscribe to newsletter", description = "Publicly accessible. Rate-limited to 60 req/min/IP.")
    @PostMapping("/subscribe")
    public ResponseEntity<ApiResponse<Void>> subscribe(@Valid @RequestBody NewsletterSubscribeRequest req) {
        repo.save(Newsletter.builder()
                .websiteId(req.getWebsiteId())
                .pageId(req.getPageId())
                .value(req.getEmail())
                .isDeleted(0)
                .build());
        log.info("Newsletter subscription for website {}", req.getWebsiteId());
        return ResponseEntity.ok(ApiResponse.ok("Subscribed successfully", null));
    }

    @Operation(summary = "List newsletter subscribers (ADMIN only)", description = "Requires ADMIN or SUPER_ADMIN role.")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<List<Newsletter>>> list(@RequestParam Long websiteId) {
        return ResponseEntity.ok(ApiResponse.ok(
                repo.findByWebsiteIdAndIsDeletedOrderByCreatedAtDesc(websiteId, 0)));
    }
}

