package com.desss.zcms.controller;

import com.desss.zcms.config.EncryptionService;
import com.desss.zcms.dto.*;
import com.desss.zcms.entity.SmsConfiguration;
import com.desss.zcms.repository.SmsConfigurationRepository;
import com.desss.zcms.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/sms")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
@Tag(name = "SMS Config (Twilio)", description = "Manage Twilio SMS configuration")
@SecurityRequirement(name = "bearerAuth")
public class SmsController {
    private final SmsConfigurationRepository repo;
    private final EncryptionService encryptionService;
    private final SmsService smsService;

    // ✅ SAVE / UPDATE
    @PostMapping
    @Operation(summary = "Save Twilio config")
    public ResponseEntity<ApiResponse<SmsConfigResponse>> save(@RequestBody SmsConfigRequest req) {
        SmsConfiguration sc = repo.findByWebsiteIdAndStatus(req.getWebsiteId(), 1)
                .orElse(new SmsConfiguration());
        sc.setWebsiteId(req.getWebsiteId());
        if (req.getAccountSid() != null) {
            sc.setAccountSid(encryptionService.encrypt(req.getAccountSid()));
        }
        if (req.getAuthToken() != null) {
            sc.setAuthToken(encryptionService.encrypt(req.getAuthToken()));
        }
        sc.setTwilioNumber(req.getTwilioNumber());
        sc.setStatus(req.getStatus());
        return ResponseEntity.ok(
                ApiResponse.ok("Saved successfully", toResponse(repo.save(sc)))
        );
    }

    // ✅ GET CONFIG
    @GetMapping("/{websiteId}")
    @Operation(summary = "Get Twilio config")
    public ResponseEntity<ApiResponse<SmsConfigResponse>> get(@PathVariable Long websiteId) {

        SmsConfiguration sc = repo.findByWebsiteIdAndStatus(websiteId, 1)
                .orElseThrow(() -> new RuntimeException("SMS config not found"));

        return ResponseEntity.ok(ApiResponse.ok("Fetched", toResponse(sc)));
    }

    // ✅ DELETE (SOFT)
    @DeleteMapping("/{websiteId}")
    @Operation(summary = "Delete Twilio config")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long websiteId) {

        SmsConfiguration sc = repo.findByWebsiteIdAndStatus(websiteId, 1)
                .orElseThrow(() -> new RuntimeException("SMS config not found"));

        sc.setStatus(0);
        repo.save(sc);

        return ResponseEntity.ok(ApiResponse.ok("Deleted successfully"));
    }

    // ✅ SEND SMS
    @PostMapping("/send")
    @Operation(summary = "Send SMS using Twilio")
    public ResponseEntity<ApiResponse<String>> sendSms(@RequestBody SmsSendRequest request) {
        smsService.send(request);
        return ResponseEntity.ok(ApiResponse.ok("SMS sent successfully"));
    }

    // ✅ MAPPER
    private SmsConfigResponse toResponse(SmsConfiguration s) {
        SmsConfigResponse smsConfigResponse = new SmsConfigResponse();
        smsConfigResponse.setId(s.getId());
        smsConfigResponse.setWebsiteId(s.getWebsiteId());
        smsConfigResponse.setAccountSid("****"); // masked
        smsConfigResponse.setTwilioNumber(s.getTwilioNumber());
        smsConfigResponse.setStatus(s.getStatus());
        return smsConfigResponse;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<String>> sendOtp(@RequestBody Map<String, String> request) {

        Long websiteId = Long.parseLong(request.get("websiteId"));
        String mobileNumber = request.get("mobileNumber");

        String otp = smsService.sendOtp(websiteId, mobileNumber);

        return ResponseEntity.ok(ApiResponse.ok("OTP sent successfully", otp));
    }
}