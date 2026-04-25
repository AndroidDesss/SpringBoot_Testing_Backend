package com.desss.zcms.controller;

import com.desss.zcms.dto.*;
import com.desss.zcms.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/testimonials")
@RequiredArgsConstructor
@Tag(name = "Public - Testimonials", description = "Public testimonial access")
public class PublicTestimonialController {
    private final TestimonialService service;

    @Operation(summary = "List testimonials (public)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TestimonialResponse>>> list(@RequestParam Long websiteId) {
        return ResponseEntity.ok(ApiResponse.ok(service.list(websiteId)));
    }
}


