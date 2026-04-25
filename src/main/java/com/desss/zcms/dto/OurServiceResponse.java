package com.desss.zcms.dto;

import lombok.Data;

@Data
public class OurServiceResponse {
    Long id;
    Long pageId;
    String image;
    String imageAlt;
    String imageTitle;
    String title;
    String titleColor;
    String redirectUrl;
    Integer openNewTab;
    Integer sortOrder;
    Integer status;
}
