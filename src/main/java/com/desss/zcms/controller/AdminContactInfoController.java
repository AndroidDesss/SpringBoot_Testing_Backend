package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.ContactInfoRequest;
import com.desss.zcms.dto.ContactInfoResponse;
import com.desss.zcms.service.ContactInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// ─────────────────────────────────────────────────────────────────────────────
//  Contact Info
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/admin/contact-info")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - Contact Info", description = "Manage website contact information")
@SecurityRequirement(name = "bearerAuth")
public class AdminContactInfoController {
    private final ContactInfoService service;

    @Operation(summary = "Get contact info")
    @GetMapping
    public ResponseEntity<ApiResponse<ContactInfoResponse>> get(@RequestParam Long websiteId) {
        return ResponseEntity.ok(ApiResponse.ok(service.get(websiteId).orElse(null)));
    }

    @Operation(summary = "Upsert contact info")
    @PostMapping
    public ResponseEntity<ApiResponse<ContactInfoResponse>> upsert(@Valid @RequestBody ContactInfoRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Contact info saved", service.upsert(req)));
    }
}
