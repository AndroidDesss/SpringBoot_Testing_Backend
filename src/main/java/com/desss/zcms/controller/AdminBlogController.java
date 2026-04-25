package com.desss.zcms.controller;

import com.desss.zcms.dto.*;
import com.desss.zcms.service.BlogService;
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
//  Blog
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/admin/blogs")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - Blogs", description = "Manage blog posts and categories")
@SecurityRequirement(name = "bearerAuth")
public class AdminBlogController {
    private final BlogService service;

    @Operation(summary = "List blogs (paginated)")
    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<BlogResponse>>> list(
            @RequestParam Long websiteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.ok(service.list(websiteId, page, size)));
    }

    @Operation(summary = "Create blog post")
    @PostMapping
    public ResponseEntity<ApiResponse<BlogResponse>> create(@Valid @RequestBody BlogRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Blog created", service.create(req)));
    }

    @Operation(summary = "Update blog post")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BlogResponse>> update(@PathVariable Long id, @Valid @RequestBody BlogRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Blog updated", service.update(id, req)));
    }

    @Operation(summary = "Delete blog post (soft delete)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Blog deleted", null));
    }

    @Operation(summary = "List blog categories")
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<BlogCategoryResponse>>> categories(@RequestParam Long websiteId) {
        return ResponseEntity.ok(ApiResponse.ok(service.listCategories(websiteId)));
    }

    @Operation(summary = "Create blog category")
    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<BlogCategoryResponse>> createCategory(@Valid @RequestBody BlogCategoryRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Category created", service.createCategory(req)));
    }
}
