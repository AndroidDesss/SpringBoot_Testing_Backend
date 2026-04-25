package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.FaqRequest;
import com.desss.zcms.dto.FaqResponse;
import com.desss.zcms.service.FaqService;
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
//  FAQ
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/admin/faqs")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - FAQs", description = "Manage FAQ entries")
@SecurityRequirement(name = "bearerAuth")
public class AdminFaqController {
    private final FaqService service;

    @Operation(summary = "List FAQs by page")
    @GetMapping
    public ResponseEntity<ApiResponse<List<FaqResponse>>> list(@RequestParam Long pageId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getByPage(pageId)));
    }

    @Operation(summary = "Create FAQ")
    @PostMapping
    public ResponseEntity<ApiResponse<FaqResponse>> create(@Valid @RequestBody FaqRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("FAQ created", service.create(req)));
    }

    @Operation(summary = "Update FAQ")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FaqResponse>> update(@PathVariable Long id, @Valid @RequestBody FaqRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("FAQ updated", service.update(id, req)));
    }

    @Operation(summary = "Delete FAQ (soft delete)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("FAQ deleted", null));
    }
}
