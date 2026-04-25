package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.TestimonialRequest;
import com.desss.zcms.dto.TestimonialResponse;
import com.desss.zcms.service.TestimonialService;
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
//  Testimonial
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/admin/testimonials")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - Testimonials", description = "Manage testimonials")
@SecurityRequirement(name = "bearerAuth")
public class AdminTestimonialController {
    private final TestimonialService service;

    @Operation(summary = "List testimonials")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TestimonialResponse>>> list(@RequestParam Long websiteId) {
        return ResponseEntity.ok(ApiResponse.ok(service.list(websiteId)));
    }

    @Operation(summary = "Create testimonial")
    @PostMapping
    public ResponseEntity<ApiResponse<TestimonialResponse>> create(@Valid @RequestBody TestimonialRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Testimonial created", service.create(req)));
    }

    @Operation(summary = "Update testimonial")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TestimonialResponse>> update(@PathVariable Long id, @Valid @RequestBody TestimonialRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Testimonial updated", service.update(id, req)));
    }

    @Operation(summary = "Delete testimonial (soft delete)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Testimonial deleted", null));
    }
}
