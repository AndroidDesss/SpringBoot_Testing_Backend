package com.desss.zcms.dto;

import lombok.Data;

@Data
public class IntroductionResponse {
    Long id;
    Long pageId;
    String title;
    String text;
    String titleColor;
    String titlePosition;
    String contentColor;
    String contentPosition;
}
