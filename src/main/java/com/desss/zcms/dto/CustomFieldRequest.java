package com.desss.zcms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomFieldRequest {
    @NotNull
    Long websiteId;
    Long pageId;          // null = global for website
    @NotBlank
    String fieldKey;
    @NotBlank
    String label;
    String fieldType = "text";
    String options;        // JSON array string
    String placeholder;
    Boolean isRequired = false;
    Integer sortOrder = 0;
}
