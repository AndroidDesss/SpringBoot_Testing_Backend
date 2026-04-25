package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.ContactFormRequest;
import com.desss.zcms.entity.ContactUs;
import com.desss.zcms.repository.ContactUsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// ─────────────────────────────────────────────────────────────────────────────
//  Public Contact Form
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/public/contact")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Public - Contact", description = "Contact form submission and admin listing of submissions")
public class PublicContactController {

    private final ContactUsRepository contactRepo;
    private final ObjectMapper mapper;

    @Operation(summary = "Submit contact form", description = "Publicly accessible. Input is sanitized before storage. Rate-limited to 60 req/min/IP.")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> submit(@Valid @RequestBody ContactFormRequest req) {
        try {
            Map<String, String> fields = new LinkedHashMap<>();
            fields.put("Name", sanitize(req.getName()));
            fields.put("Email", sanitize(req.getEmail()));
            fields.put("Phone", req.getPhone() != null ? sanitize(req.getPhone()) : "");
            fields.put("Message", sanitize(req.getMessage()));

            if (req.getExtraFields() != null) {
                req.getExtraFields().forEach((k, v) -> fields.put(sanitize(k), sanitize(v)));
            }

            String keyJson = mapper.writeValueAsString(List.copyOf(fields.keySet()));
            String valueJson = mapper.writeValueAsString(List.copyOf(fields.values()));

            contactRepo.save(ContactUs.builder()
                    .websiteId(req.getWebsiteId())
                    .fieldKey(keyJson)
                    .fieldValue(valueJson)
                    .isDeleted(0)
                    .createdAt(LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")))
                    .build());

            log.info("Contact form submitted for website {}", req.getWebsiteId());
            return ResponseEntity.ok(ApiResponse.ok("Message sent successfully", null));
        } catch (Exception e) {
            log.error("Contact form error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.error("Failed to submit form"));
        }
    }

    @Operation(summary = "List contact submissions (ADMIN only)", description = "Requires ADMIN or SUPER_ADMIN role. Previously unauthenticated — now secured.")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/submissions")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<List<ContactUs>>> list(@RequestParam Long websiteId) {
        return ResponseEntity.ok(ApiResponse.ok(
                contactRepo.findByWebsiteIdAndIsDeletedOrderByIdDesc(websiteId, 0)));
    }

    private String sanitize(String input) {
        if (input == null) return null;
        return input
                .replaceAll("<[^>]*>", "")
                .replaceAll("javascript:", "")
                .replaceAll("on\\w+\\s*=", "")
                .trim();
    }
}
