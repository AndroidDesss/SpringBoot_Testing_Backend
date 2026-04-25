package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.ContactInfoResponse;
import com.desss.zcms.service.ContactInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/contact-info")
@RequiredArgsConstructor
@Tag(name = "Public - Contact Info", description = "Public contact information access")
public class PublicContactInfoController {
    private final ContactInfoService service;

    @Operation(summary = "Get contact info (public)")
    @GetMapping
    public ResponseEntity<ApiResponse<ContactInfoResponse>> get(@RequestParam Long websiteId) {
        return ResponseEntity.ok(ApiResponse.ok(service.get(websiteId).orElse(null)));
    }
}
