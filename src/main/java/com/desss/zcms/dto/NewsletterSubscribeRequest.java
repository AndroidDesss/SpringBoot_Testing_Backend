package com.desss.zcms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// ── Newsletter Subscribe ──────────────────────────────────────────────────────
@Data
public class NewsletterSubscribeRequest {
    @NotNull
    Long websiteId;
    Long pageId;
    @NotBlank
    @Email
    String email;
}
