package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.PageDto;
import com.desss.zcms.service.PageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/pages")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - Pages", description = "Page management — create, update, publish and delete pages")
@SecurityRequirement(name = "bearerAuth")
public class PageController {

    private final PageService pageService;

    @Operation(summary = "List all pages for a website")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PageDto>>> list(@RequestParam Long websiteId) {
        return ResponseEntity.ok(ApiResponse.ok(pageService.listByWebsite(websiteId)));
    }

    @Operation(summary = "Get page by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PageDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(pageService.getById(id)));
    }

    @Operation(summary = "Create a new page")
    @PostMapping
    public ResponseEntity<ApiResponse<PageDto>> create(@RequestBody PageDto req) {
        return ResponseEntity.ok(ApiResponse.ok("Page created", pageService.create(req)));
    }

    @Operation(summary = "Update a page")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PageDto>> update(@PathVariable Long id, @RequestBody PageDto req) {
        return ResponseEntity.ok(ApiResponse.ok("Page updated", pageService.update(id, req)));
    }

    @Operation(summary = "Publish or unpublish a page")
    @PatchMapping("/{id}/publish")
    public ResponseEntity<ApiResponse<PageDto>> publish(
        @PathVariable Long id, @RequestParam boolean publish) {
        return ResponseEntity.ok(ApiResponse.ok(
            publish ? "Page published" : "Page unpublished",
            pageService.publish(id, publish)));
    }

    @Operation(summary = "Soft-delete a page")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        pageService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Page deleted", null));
    }
}
