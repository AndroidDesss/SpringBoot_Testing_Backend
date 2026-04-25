package com.desss.zcms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// ── Introduction ──────────────────────────────────────────────────────────────
@Data
public class IntroductionRequest {
    @NotNull
    Long pageId;
    String title;
    String text;
    String titleColor;
    String titlePosition;
    String contentColor;
    String contentPosition;
}
