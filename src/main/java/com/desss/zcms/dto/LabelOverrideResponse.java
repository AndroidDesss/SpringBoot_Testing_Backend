package com.desss.zcms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LabelOverrideResponse {
    Long id;
    Long websiteId;
    Long pageId;
    String fieldKey;
    String customLabel;
    LocalDateTime createdAt;
}
