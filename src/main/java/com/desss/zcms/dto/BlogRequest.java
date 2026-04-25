package com.desss.zcms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// ── Blog ──────────────────────────────────────────────────────────────────────
@Data
public class BlogRequest {
    @NotNull
    Long websiteId;
    Long categoryId;
    @NotBlank
    String title;
    String image;
    String imageAlt;
    String imageTitle;
    String shortDescription;
    String description;
    String url;
    String pageExtension = ".html";
    String metaTitle;
    String metaDescription;
    String metaKeyword;
    String h1Tag;
    String h2Tag;
    Integer sortOrder = 0;
    Integer status = 1;
}
