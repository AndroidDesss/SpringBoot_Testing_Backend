package com.desss.zcms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomButtonRequest {
    @NotNull
    Long websiteId;
    Long pageId;
    @NotBlank
    String buttonName;
    @NotBlank
    String buttonType;   // navigation|form_submit|api_call|redirect|custom_js
    String targetUrl;
    String apiMethod;
    String apiEndpoint;
    String apiPayload;
    String customJs;
    String styleVariant = "primary";
    String iconName;
    String linkTarget = "_self";
    Integer sortOrder = 0;
    Boolean isActive = true;
}
