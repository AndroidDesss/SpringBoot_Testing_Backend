package com.desss.zcms.controller;

import com.desss.zcms.config.EncryptionService;
import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.PaymentConfigRequest;
import com.desss.zcms.entity.PaymentConfiguration;
import com.desss.zcms.repository.PaymentConfigurationRepository;
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
@RequestMapping("/api/admin/payment-config")
@RequiredArgsConstructor

@PreAuthorize("hasRole('SUPER_ADMIN')")
@Tag(name = "Super Admin - Payment Config")
@SecurityRequirement(name = "bearerAuth")

public class PaymentConfigController {

    private final PaymentConfigurationRepository repo;
    private final EncryptionService encryptionService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@RequestBody PaymentConfigRequest req) {

        PaymentConfiguration pc = repo.findByWebsiteIdAndStatus(req.getWebsiteId(), 1)
                .orElse(new PaymentConfiguration());

        pc.setWebsiteId(req.getWebsiteId());
        pc.setProviderName(req.getProviderName());
        pc.setApiLoginId(req.getApiLoginId());
        pc.setEnvironment(req.getEnvironment());

        if (req.getTransactionKey() != null) {
            pc.setTransactionKey(encryptionService.encrypt(req.getTransactionKey()));
        }

        pc.setStatus(req.getStatus());

        repo.save(pc);

        return ResponseEntity.ok(ApiResponse.ok("Payment config saved"));
    }
}
