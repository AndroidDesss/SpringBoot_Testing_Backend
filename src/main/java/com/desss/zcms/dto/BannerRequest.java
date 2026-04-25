package com.desss.zcms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// ── Banner ────────────────────────────────────────────────────────────────────
@Data
public class BannerRequest {
    @NotNull
    Long pageId;
    String title;
    String titleColor;
    String text;
    String textColor;
    String image;
    String mobileImage;
    String imageAlt;
    String imageTitle;
    Integer readmoreBtn = 0;
    String readmoreLabel;
    String readmoreUrl;
    Integer openNewTab = 0;
    String buttonType;
    String buttonPosition;
    String btnBackgroundColor;
    String labelColor;
    String textPosition;
    Integer sortOrder = 0;
    Integer status = 1;
}
