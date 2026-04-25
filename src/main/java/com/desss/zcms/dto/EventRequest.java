package com.desss.zcms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// ── Event ─────────────────────────────────────────────────────────────────────
@Data
public class EventRequest {
    @NotNull
    Long websiteId;
    Long categoryId;
    @NotBlank
    String title;
    String image;
    String imageAlt;
    String shortDescription;
    String description;
    String url;
    String eventDate;
    String metaTitle;
    String metaDescription;
    Integer sortOrder = 0;
    Integer status = 1;
}
