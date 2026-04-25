package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.BannerResponse;
import com.desss.zcms.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/banners")
@RequiredArgsConstructor
@Tag(name = "Public - Banners", description = "Public banner access")
public class PublicBannerController {
    private final BannerService service;

    @Operation(summary = "List banners by page (public)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BannerResponse>>> list(@RequestParam Long pageId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getByPage(pageId)));
    }
}
