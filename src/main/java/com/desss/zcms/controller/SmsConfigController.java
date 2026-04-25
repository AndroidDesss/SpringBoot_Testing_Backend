package com.desss.zcms.controller;

import com.desss.zcms.config.EncryptionService;
import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.SmsConfigRequest;
import com.desss.zcms.dto.SmsConfigResponse;
import com.desss.zcms.entity.SmsConfiguration;
import com.desss.zcms.repository.SmsConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/admin/sms-config")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
@Tag(
        name = "Super Admin - SMS Config",
        description = "Twilio SMS configuration (SUPER_ADMIN only). Credentials are stored encrypted."
)
@SecurityRequirement(name = "bearerAuth")
public class SmsConfigController {

    private final SmsConfigurationRepository repo;
    private final EncryptionService encryptionService;

    // ✅ SAVE OR UPDATE CONFIG
    @PostMapping
    @Operation(summary = "Save Twilio config", description = "Creates or updates Twilio configuration.")
    public ResponseEntity<ApiResponse<SmsConfigResponse>> save(@RequestBody SmsConfigRequest req) {

        SmsConfiguration sc = repo.findByWebsiteIdAndStatus(req.getWebsiteId(), 1)
                .orElse(new SmsConfiguration());

        sc.setWebsiteId(req.getWebsiteId());

        // ✅ Encrypt sensitive fields
        if (req.getAccountSid() != null) {
            sc.setAccountSid(encryptionService.encrypt(req.getAccountSid()));
        }

        if (req.getAuthToken() != null) {
            sc.setAuthToken(encryptionService.encrypt(req.getAuthToken()));
        }

        sc.setTwilioNumber(req.getTwilioNumber());
        sc.setStatus(req.getStatus());

        SmsConfiguration saved = repo.save(sc);

        return ResponseEntity.ok(ApiResponse.ok("Saved successfully", toResponse(saved)));
    }

    // ✅ GET CONFIG BY WEBSITE ID
    @GetMapping("/{websiteId}")
    @Operation(summary = "Get Twilio config", description = "Fetch Twilio configuration by websiteId.")
    public ResponseEntity<ApiResponse<SmsConfigResponse>> get(@PathVariable Long websiteId) {

        SmsConfiguration sc = repo.findByWebsiteIdAndStatus(websiteId, 1)
                .orElseThrow(() -> new RuntimeException("SMS config not found"));

        return ResponseEntity.ok(ApiResponse.ok("Fetched successfully", toResponse(sc)));
    }

    // ✅ DELETE CONFIG (SOFT DELETE)
    @DeleteMapping("/{websiteId}")
    @Operation(summary = "Delete Twilio config", description = "Soft delete Twilio config by setting status = 0.")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long websiteId) {

        SmsConfiguration sc = repo.findByWebsiteIdAndStatus(websiteId, 1)
                .orElseThrow(() -> new RuntimeException("SMS config not found"));

        sc.setStatus(0);
        repo.save(sc);

        return ResponseEntity.ok(ApiResponse.ok("Deleted successfully"));
    }

    // ✅ RESPONSE MAPPER
    private SmsConfigResponse toResponse(SmsConfiguration s) {

        SmsConfigResponse r = new SmsConfigResponse();
        r.setId(s.getId());
        r.setWebsiteId(s.getWebsiteId());

        // 🔐 Do NOT expose sensitive data fully
        r.setAccountSid("****");
        r.setTwilioNumber(s.getTwilioNumber());

        r.setStatus(s.getStatus());

        return r;
    }
}