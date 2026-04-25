package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.PaymentRequest;
import com.desss.zcms.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/admin/payment")
@RequiredArgsConstructor

@PreAuthorize("hasRole('SUPER_ADMIN')")
@Tag(name = "Super Admin - Payment")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<ApiResponse<String>> pay(@RequestBody PaymentRequest request) {

        String response = paymentService.makePayment(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
