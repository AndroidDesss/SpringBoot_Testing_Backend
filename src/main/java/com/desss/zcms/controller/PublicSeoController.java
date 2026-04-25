package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.SeoResponse;
import com.desss.zcms.service.SeoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/seo")
@RequiredArgsConstructor
@Tag(name = "Public - SEO", description = "Public SEO data access")
public class PublicSeoController {
    private final SeoService service;

    @Operation(summary = "Get SEO data by page (public)")
    @GetMapping
    public ResponseEntity<ApiResponse<SeoResponse>> get(@RequestParam Long pageId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getByPage(pageId).orElse(null)));
    }
}
