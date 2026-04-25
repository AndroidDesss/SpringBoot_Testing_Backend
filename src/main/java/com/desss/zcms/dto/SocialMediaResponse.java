package com.desss.zcms.dto;

import lombok.Data;

@Data
public class SocialMediaResponse {
    Long mediaId;
    Long websiteId;
    String mediaName;
    String mediaUrl;
    String icon;
    String iconColor;
    Integer sortOrder;
    Integer status;
}
