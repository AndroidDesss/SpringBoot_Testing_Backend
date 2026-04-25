package com.desss.zcms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomButtonResponse {
    Long id;
    Long websiteId;
    Long pageId;
    String buttonName;
    String buttonType;
    String targetUrl;
    String apiMethod;
    String apiEndpoint;
    String apiPayload;
    String customJs;
    String styleVariant;
    String iconName;
    String linkTarget;
    Integer sortOrder;
    Boolean isActive;
    LocalDateTime createdAt;
}
