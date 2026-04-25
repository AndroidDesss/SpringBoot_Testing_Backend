package com.desss.zcms.controller;

import com.desss.zcms.dto.*;
import com.desss.zcms.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// ─────────────────────────────────────────────────────────────────────────────
//  Resolved Page Config  /api/admin/pages/{pageId}/config
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/admin/pages")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - Pages", description = "Page management and resolved config")
@SecurityRequirement(name = "bearerAuth")
public class PageConfigController {

    private final CustomFieldService fieldService;
    private final CustomButtonService buttonService;
    private final LabelOverrideService labelService;

    @Operation(summary = "Get resolved page config",
            description = "Returns all fields, buttons, and label map for a page — with all overrides resolved.")
    @GetMapping("/{pageId}/config")
    public ResponseEntity<ApiResponse<ResolvedPageConfig>> config(
            @PathVariable Long pageId, @RequestParam Long websiteId) {
        ResolvedPageConfig cfg = new ResolvedPageConfig();
        cfg.setPageId(pageId);
        cfg.setWebsiteId(websiteId);
        cfg.setFields(fieldService.getResolved(websiteId, pageId));
        cfg.setButtons(buttonService.getResolved(websiteId, pageId));
        cfg.setLabelMap(labelService.resolvedLabelMap(websiteId, pageId));
        return ResponseEntity.ok(ApiResponse.ok(cfg));
    }
}

