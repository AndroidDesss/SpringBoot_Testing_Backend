package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.FedexRateRequest;
import com.desss.zcms.service.FedexService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/shipping/fedex")
@RequiredArgsConstructor

@PreAuthorize("hasRole('SUPER_ADMIN')")
@SecurityRequirement(name = "bearerAuth")

public class FedexController {

    private final FedexService service;

    @PostMapping("/rates")
    public ResponseEntity<ApiResponse<String>> getRates(
            @RequestBody FedexRateRequest request) {

        return ResponseEntity.ok(ApiResponse.ok(service.getRates(request)));
    }
}
