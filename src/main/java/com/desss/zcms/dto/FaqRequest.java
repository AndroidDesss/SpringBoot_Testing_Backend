package com.desss.zcms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// ── FAQ ───────────────────────────────────────────────────────────────────────
@Data
public class FaqRequest {
    @NotNull
    Long pageId;
    String category;
    @NotBlank
    String faqQuestion;
    @NotBlank
    String faqAnswer;
    String sortOrder = "0";
    Integer status = 1;
}
