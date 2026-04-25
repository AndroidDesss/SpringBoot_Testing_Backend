package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.DashboardStats;
import com.desss.zcms.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// ─────────────────────────────────────────────────────────────────────────────
//  Dashboard
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - Dashboard", description = "Dashboard statistics")
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {
    private final DashboardService service;

    @Operation(summary = "Get dashboard stats", description = "Returns counts for pages, blogs, events, testimonials, contacts, newsletter subscribers, custom fields and buttons.")
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<DashboardStats>> stats(@RequestParam Long websiteId) {
        return ResponseEntity.ok(ApiResponse.ok(service.stats(websiteId)));
    }
}
