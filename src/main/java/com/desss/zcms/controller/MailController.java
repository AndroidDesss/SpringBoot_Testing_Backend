package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.MailSendRequest;
import com.desss.zcms.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/admin/mail")
@RequiredArgsConstructor

@PreAuthorize("hasRole('SUPER_ADMIN')")
@Tag(name = "Super Admin - Mail", description = "Send emails (SUPER_ADMIN only)")
@SecurityRequirement(name = "bearerAuth")

public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    @Operation(summary = "Send Mail", description = "Send email using configured SMTP")
    public ResponseEntity<ApiResponse<String>> sendMail(
            @RequestBody MailSendRequest request) {

        mailService.send(
                request.getWebsiteId(),
                request.getToEmail(),
                request.getSubject(),
                request.getBody()
        );

        return ResponseEntity.ok(ApiResponse.ok("Mail sent successfully"));
    }
}
