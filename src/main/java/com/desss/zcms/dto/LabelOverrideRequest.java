package com.desss.zcms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LabelOverrideRequest {
    @NotNull
    Long websiteId;
    Long pageId;          // null = global
    @NotBlank
    String fieldKey;
    @NotBlank
    String customLabel;
}
