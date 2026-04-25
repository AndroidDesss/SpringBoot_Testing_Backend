package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.LabelOverrideRequest;
import com.desss.zcms.dto.LabelOverrideResponse;
import com.desss.zcms.service.LabelOverrideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// ─────────────────────────────────────────────────────────────────────────────
//  Label Override Controller   /api/admin/label-overrides
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/admin/label-overrides")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - Label Overrides", description = "Customise field labels per website/page")
@SecurityRequirement(name = "bearerAuth")
public class LabelOverrideController {

    private final LabelOverrideService service;

    @Operation(summary = "Get resolved label map", description = "Returns { fieldKey: customLabel } map. Page-level overrides win over global.")
    @GetMapping("/map")
    public ResponseEntity<ApiResponse<Map<String, String>>> labelMap(
            @RequestParam Long websiteId,
            @RequestParam(required = false) Long pageId) {
        return ResponseEntity.ok(ApiResponse.ok(service.resolvedLabelMap(websiteId, pageId)));
    }

    @Operation(summary = "Upsert label override")
    @PostMapping
    public ResponseEntity<ApiResponse<LabelOverrideResponse>> upsert(@Valid @RequestBody LabelOverrideRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Label saved", service.upsert(req)));
    }

    @Operation(summary = "Delete label override")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Label override removed", null));
    }
}
