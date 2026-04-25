package com.desss.zcms.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomFieldResponse {
    Long id;
    Long websiteId;
    Long pageId;
    String fieldKey;
    String label;
    String fieldType;
    List<String> options;
    String placeholder;
    Boolean isRequired;
    Integer sortOrder;
    LocalDateTime createdAt;
}
