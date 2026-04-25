package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.PageComponentBulkRequest;
import com.desss.zcms.dto.PageComponentResponse;
import com.desss.zcms.service.PageComponentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/page-components")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - Page Components")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class PageComponentController {

    private final PageComponentService service;

    @Operation(summary = "List active components for a page (sorted by sort_order)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PageComponentResponse>>> list(@RequestParam Long pageId) {
        return ResponseEntity.ok(ApiResponse.ok(service.listByPage(pageId)));
    }

    @Operation(summary = "Hard delete a single component")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Component deleted", null));
    }

    @Operation(summary = "Bulk save — hard DELETE old rows + INSERT new ordered rows")
    @PostMapping("/bulk-save")
    public ResponseEntity<ApiResponse<List<PageComponentResponse>>> bulkSave(
            @RequestBody PageComponentBulkRequest req) {

        if (req.getPageId() == null || req.getWebsiteId() == null) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("pageId and websiteId are required"));
        }

        return ResponseEntity.ok(ApiResponse.ok("Page saved", service.bulkSave(req)));
    }
}