package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.SocialMediaRequest;
import com.desss.zcms.dto.SocialMediaResponse;
import com.desss.zcms.service.SocialMediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ─────────────────────────────────────────────────────────────────────────────
//  Social Media
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/admin/social-media")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - Social Media", description = "Manage social media links")
@SecurityRequirement(name = "bearerAuth")
public class AdminSocialMediaController {
    private final SocialMediaService service;

    @Operation(summary = "List social media links")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SocialMediaResponse>>> list(@RequestParam Long websiteId) {
        return ResponseEntity.ok(ApiResponse.ok(service.list(websiteId)));
    }

    @Operation(summary = "Create social media link")
    @PostMapping
    public ResponseEntity<ApiResponse<SocialMediaResponse>> create(@Valid @RequestBody SocialMediaRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Social media created", service.create(req)));
    }

    @Operation(summary = "Update social media link")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SocialMediaResponse>> update(@PathVariable Long id, @Valid @RequestBody SocialMediaRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Updated", service.update(id, req)));
    }

    @Operation(summary = "Delete social media link (soft delete)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Deleted", null));
    }
}
