package com.desss.zcms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// ── Blog Category ─────────────────────────────────────────────────────────────
@Data
public class BlogCategoryRequest {
    @NotNull
    Long websiteId;
    @NotBlank
    String categoryName;
    String url;
    Integer status = 1;
}
