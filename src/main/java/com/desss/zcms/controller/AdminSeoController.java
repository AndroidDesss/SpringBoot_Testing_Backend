package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.SeoRequest;
import com.desss.zcms.dto.SeoResponse;
import com.desss.zcms.service.SeoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// ─────────────────────────────────────────────────────────────────────────────
//  SEO
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/admin/seo")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - SEO", description = "Manage SEO metadata per page")
@SecurityRequirement(name = "bearerAuth")
public class AdminSeoController {
    private final SeoService service;

    @Operation(summary = "Get SEO data by page")
    @GetMapping
    public ResponseEntity<ApiResponse<SeoResponse>> get(@RequestParam Long pageId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getByPage(pageId).orElse(null)));
    }

    @Operation(summary = "Upsert SEO data")
    @PostMapping
    public ResponseEntity<ApiResponse<SeoResponse>> upsert(@Valid @RequestBody SeoRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("SEO saved", service.upsert(req)));
    }
}
