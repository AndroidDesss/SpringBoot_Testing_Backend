package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.CustomFieldRequest;
import com.desss.zcms.dto.CustomFieldResponse;
import com.desss.zcms.service.CustomFieldService;
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
//  Custom Field Controller   /api/admin/custom-fields
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/admin/custom-fields")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - Custom Fields", description = "Manage custom field definitions per website/page")
@SecurityRequirement(name = "bearerAuth")
public class CustomFieldController {

    private final CustomFieldService service;

    @Operation(summary = "List resolved fields", description = "Returns merged fields — page-level overrides global for the same fieldKey.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomFieldResponse>>> list(
            @RequestParam Long websiteId,
            @RequestParam(required = false) Long pageId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getResolved(websiteId, pageId)));
    }

    @Operation(summary = "Create custom field")
    @PostMapping
    public ResponseEntity<ApiResponse<CustomFieldResponse>> create(@Valid @RequestBody CustomFieldRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Field created", service.create(req)));
    }

    @Operation(summary = "Update custom field")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomFieldResponse>> update(
            @PathVariable Long id, @Valid @RequestBody CustomFieldRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Field updated", service.update(id, req)));
    }

    @Operation(summary = "Delete custom field (soft delete)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Field deleted", null));
    }
}
