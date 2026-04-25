package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.PaypalRequest;
import com.desss.zcms.service.PaypalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/payment/paypal")
@RequiredArgsConstructor

@PreAuthorize("hasRole('SUPER_ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class PaypalController {

    private final PaypalService service;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> create(
            @RequestBody PaypalRequest request) {

        return ResponseEntity.ok(ApiResponse.ok(service.createPayment(request)));
    }
}
