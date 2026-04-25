package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.CustomButtonRequest;
import com.desss.zcms.dto.CustomButtonResponse;
import com.desss.zcms.service.CustomButtonService;
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
//  Custom Button Controller   /api/admin/custom-buttons
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/admin/custom-buttons")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - Custom Buttons", description = "Manage custom action buttons per website/page")
@SecurityRequirement(name = "bearerAuth")
public class CustomButtonController {

    private final CustomButtonService service;

    @Operation(summary = "List resolved buttons")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomButtonResponse>>> list(
            @RequestParam Long websiteId,
            @RequestParam(required = false) Long pageId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getResolved(websiteId, pageId)));
    }

    @Operation(summary = "Create custom button")
    @PostMapping
    public ResponseEntity<ApiResponse<CustomButtonResponse>> create(@Valid @RequestBody CustomButtonRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Button created", service.create(req)));
    }

    @Operation(summary = "Update custom button")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomButtonResponse>> update(
            @PathVariable Long id, @Valid @RequestBody CustomButtonRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Button updated", service.update(id, req)));
    }

    @Operation(summary = "Delete custom button (soft delete)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Button deleted", null));
    }
}
