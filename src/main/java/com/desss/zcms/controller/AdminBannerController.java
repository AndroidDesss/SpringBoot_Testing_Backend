package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.BannerRequest;
import com.desss.zcms.dto.BannerResponse;
import com.desss.zcms.service.BannerService;
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
//  Banner
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/admin/banners")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - Banners", description = "Manage page banners")
@SecurityRequirement(name = "bearerAuth")
public class AdminBannerController {
    private final BannerService service;

    @Operation(summary = "List banners by page")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BannerResponse>>> list(@RequestParam Long pageId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getByPage(pageId)));
    }

    @Operation(summary = "Create banner")
    @PostMapping
    public ResponseEntity<ApiResponse<BannerResponse>> create(@Valid @RequestBody BannerRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Banner created", service.create(req)));
    }

    @Operation(summary = "Update banner")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BannerResponse>> update(@PathVariable Long id, @Valid @RequestBody BannerRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Banner updated", service.update(id, req)));
    }

    @Operation(summary = "Delete banner (soft delete)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Banner deleted", null));
    }
}
