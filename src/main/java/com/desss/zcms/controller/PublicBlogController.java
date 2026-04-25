package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.BlogCategoryResponse;
import com.desss.zcms.dto.BlogResponse;
import com.desss.zcms.dto.PaginatedResponse;
import com.desss.zcms.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/blogs")
@RequiredArgsConstructor
@Tag(name = "Public - Blogs", description = "Public blog access")
public class PublicBlogController {
    private final BlogService service;

    @Operation(summary = "List blogs (public, paginated)")
    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<BlogResponse>>> list(
            @RequestParam Long websiteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.ok(service.list(websiteId, page, size)));
    }

    @Operation(summary = "Get blog by URL slug")
    @GetMapping("/{url}")
    public ResponseEntity<ApiResponse<BlogResponse>> getByUrl(@RequestParam Long websiteId, @PathVariable String url) {
        return ResponseEntity.ok(ApiResponse.ok(service.getByUrl(websiteId, url)));
    }

    @Operation(summary = "List blog categories (public)")
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<BlogCategoryResponse>>> categories(@RequestParam Long websiteId) {
        return ResponseEntity.ok(ApiResponse.ok(service.listCategories(websiteId)));
    }
}
