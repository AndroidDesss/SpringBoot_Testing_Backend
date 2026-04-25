package com.desss.zcms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// ── Social Media ──────────────────────────────────────────────────────────────
@Data
public class SocialMediaRequest {
    @NotNull
    Long websiteId;
    @NotBlank
    String mediaName;
    @NotBlank
    String mediaUrl;
    String icon;
    String iconColor;
    Integer sortOrder = 0;
    Integer status = 1;
}
