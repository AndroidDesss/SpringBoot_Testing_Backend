package com.desss.zcms.controller;

import com.desss.zcms.config.EncryptionService;
import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.MailConfigRequest;
import com.desss.zcms.dto.MailConfigResponse;
import com.desss.zcms.entity.MailConfiguration;
import com.desss.zcms.repository.MailConfigurationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/mail-config")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
@Tag(name = "Super Admin - Mail Config", description = "SMTP mail configuration (SUPER_ADMIN only). Password is stored AES-256-GCM encrypted.")
@SecurityRequirement(name = "bearerAuth")
public class MailConfigController {

    private final MailConfigurationRepository repo;
    private final EncryptionService encryptionService;

    @Operation(summary = "Get mail config for website", description = "Password field is never returned in the response.")
    @GetMapping
    public ResponseEntity<ApiResponse<MailConfigResponse>> get(@RequestParam Long websiteId) {
        return ResponseEntity.ok(ApiResponse.ok(
            repo.findByWebsiteIdAndStatus(websiteId, 1).map(this::toResponse).orElse(null)
        ));
    }

    @Operation(summary = "Save mail config", description = "Creates or updates the SMTP config. Password is encrypted with AES-256-GCM before storage.")
    @PostMapping
    public ResponseEntity<ApiResponse<MailConfigResponse>> upsert(@Valid @RequestBody MailConfigRequest req) {
        MailConfiguration mc = repo.findByWebsiteIdAndStatus(req.getWebsiteId(), 1)
            .orElse(MailConfiguration.builder().websiteId(req.getWebsiteId()).build());

        mc.setHost(req.getHost());
        mc.setPort(req.getPort());
        mc.setEmail(req.getEmail());
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            mc.setPassword(encryptionService.encrypt(req.getPassword()));
        }
        mc.setMailFrom(req.getMailFrom());
        mc.setStatus(req.getStatus());

        return ResponseEntity.ok(ApiResponse.ok("Mail config saved", toResponse(repo.save(mc))));
    }

    private MailConfigResponse toResponse(MailConfiguration m) {
        MailConfigResponse r = new MailConfigResponse();
        r.setId(m.getId()); r.setWebsiteId(m.getWebsiteId());
        r.setHost(m.getHost()); r.setPort(m.getPort());
        r.setEmail(m.getEmail()); r.setMailFrom(m.getMailFrom());
        r.setStatus(m.getStatus());
        return r;
    }
}
