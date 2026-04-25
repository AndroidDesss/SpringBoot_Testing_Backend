package com.desss.zcms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// ── SEO ───────────────────────────────────────────────────────────────────────
@Data
public class SeoRequest {
    @NotNull
    Long pageId;
    String h1Tag;
    String h2Tag;
    String metaTitle;
    String metaDescription;
    String metaKeyword;
    String targetKeyword;
    String metaMisc;
}
