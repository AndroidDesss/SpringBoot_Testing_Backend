package com.desss.zcms.dto;

import lombok.Data;

@Data
public class EventResponse {
    Long id;
    Long websiteId;
    Long categoryId;
    String title;
    String image;
    String imageAlt;
    String shortDescription;
    String description;
    String url;
    String eventDate;
    String metaTitle;
    String metaDescription;
    Integer sortOrder;
    Integer status;
}
