package com.desss.zcms.dto;

import lombok.Data;

@Data
public class BlogResponse {
    Long id;
    Long websiteId;
    Long categoryId;
    String title;
    String image;
    String imageAlt;
    String shortDescription;
    String description;
    String url;
    String metaTitle;
    String metaDescription;
    String h1Tag;
    String h2Tag;
    Integer sortOrder;
    Integer status;
    String createdAt;
    String categoryName;
}
