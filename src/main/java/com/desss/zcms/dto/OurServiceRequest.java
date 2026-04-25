package com.desss.zcms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// ── Our Service ───────────────────────────────────────────────────────────────
@Data
public class OurServiceRequest {
    @NotNull
    Long pageId;
    String image;
    String imageAlt;
    String imageTitle;
    String title;
    String titleColor;
    String redirectUrl;
    Integer openNewTab = 0;
    Integer sortOrder = 0;
    Integer status = 1;
}
