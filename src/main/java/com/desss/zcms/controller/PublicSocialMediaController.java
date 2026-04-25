package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.SocialMediaResponse;
import com.desss.zcms.service.SocialMediaService;
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
@RequestMapping("/api/public/social-media")
@RequiredArgsConstructor
@Tag(name = "Public - Social Media", description = "Public social media access")
public class PublicSocialMediaController {
    private final SocialMediaService service;

    @Operation(summary = "List social media links (public)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SocialMediaResponse>>> list(@RequestParam Long websiteId) {
        return ResponseEntity.ok(ApiResponse.ok(service.list(websiteId)));
    }
}
