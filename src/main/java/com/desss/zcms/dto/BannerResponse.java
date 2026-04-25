package com.desss.zcms.dto;

import lombok.Data;

@Data
public class BannerResponse {
    Long id;
    Long pageId;
    String title;
    String titleColor;
    String text;
    String textColor;
    String image;
    String mobileImage;
    String imageAlt;
    String imageTitle;
    Integer readmoreBtn;
    String readmoreLabel;
    String readmoreUrl;
    Integer openNewTab;
    String buttonType;
    String buttonPosition;
    String btnBackgroundColor;
    String labelColor;
    String textPosition;
    Integer sortOrder;
    Integer status;
}
